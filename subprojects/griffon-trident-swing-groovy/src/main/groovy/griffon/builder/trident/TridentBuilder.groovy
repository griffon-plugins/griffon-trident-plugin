/*
 * Copyright 2012-2016 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package griffon.builder.trident

import griffon.builder.trident.factory.ActionTriggerFactory
import griffon.builder.trident.factory.EaseFactory
import griffon.builder.trident.factory.FocusTriggerFactory
import griffon.builder.trident.factory.InterpolatedPropertyFactory
import griffon.builder.trident.factory.KeyFrameFactory
import griffon.builder.trident.factory.KeyFramesFactory
import griffon.builder.trident.factory.MouseTriggerFactory
import griffon.builder.trident.factory.SplineEaseFactory
import griffon.builder.trident.factory.SwingRepaintTimelineFactory
import griffon.builder.trident.factory.TimelineCallbackFactory
import griffon.builder.trident.factory.TimelineFactory
import griffon.builder.trident.factory.TimelineRunnableFactory
import griffon.builder.trident.factory.TimelineScenarioCallbackFactory
import griffon.builder.trident.factory.TimelineScenarioFactory
import griffon.builder.trident.factory.UIThreadTimelineCallbackFactory
import org.hybird.animation.trident.triggers.ActionTrigger
import org.pushingpixels.trident.TimelineScenario
import org.pushingpixels.trident.ease.Linear
import org.pushingpixels.trident.ease.Sine

/**
 * @author Andres Almiray
 */
class TridentBuilder extends FactoryBuilderSupport {
    public static final String DELEGATE_PROPERTY_ACTION_TRIGGER = '_delegateProperty:actionTriggerFor';
    public static final String DEFAULT_DELEGATE_PROPERTY_ACTION_TRIGGER = 'actionTriggerFor';

    TridentBuilder(boolean init = true) {
        super(init)
        this[DELEGATE_PROPERTY_ACTION_TRIGGER] = DEFAULT_DELEGATE_PROPERTY_ACTION_TRIGGER
    }

    def registerTrident() {
        addAttributeDelegate(TridentBuilder.&objectIDAttributeDelegate)

        registerFactory('timeline', new TimelineFactory())
        registerFactory('timelineCallback', new TimelineCallbackFactory())
        registerFactory('uiThreadTimelineCallback', new UIThreadTimelineCallbackFactory())
        registerFactory('interpolatedProperty', new InterpolatedPropertyFactory())
        registerFactory('keyFrames', new KeyFramesFactory())
        registerFactory('keyFrame', new KeyFrameFactory())
        registerFactory('linearEase', new EaseFactory(Linear))
        registerFactory('sineEase', new EaseFactory(Sine))
        registerFactory('splineEase', new SplineEaseFactory())
        // scenarios
        registerFactory('timelineScenario', new TimelineScenarioFactory(TimelineScenario))
        registerFactory('parallelScenario', new TimelineScenarioFactory(TimelineScenario.Parallel))
        registerFactory('sequenceScenario', new TimelineScenarioFactory(TimelineScenario.Sequence))
        registerFactory('rendevouzScenario', new TimelineScenarioFactory(TimelineScenario.RendezvousSequence))
        registerFactory('timelineScenarioCallback', new TimelineScenarioCallbackFactory())
        //
        registerFactory('swingRepaintTimeline', new SwingRepaintTimelineFactory())
        registerFactory('timelineRunnable', new TimelineRunnableFactory())
    }

    def registerTriggers() {
        addAttributeDelegate(TridentBuilder.&actionTriggerAttributeDelegate)
        registerFactory('actionTrigger', new ActionTriggerFactory())
        registerFactory('mouseTrigger', new MouseTriggerFactory())
        registerFactory('focusTrigger', new FocusTriggerFactory())
    }

    private static actionTriggerAttributeDelegate(builder, node, attributes) {
        def attr = builder.getAt(DELEGATE_PROPERTY_ACTION_TRIGGER) ?: DEFAULT_DELEGATE_PROPERTY_ACTION_TRIGGER
        def timeline = attributes.remove(attr)
        if (timeline) {
            ActionTrigger.addTrigger(node, timeline)
        }
    }

    // taken from groovy.swing.SwingBuilder
    private static objectIDAttributeDelegate(builder, node, attributes) {
        def theID = attributes.remove('id')
        if (theID) {
            builder.setVariable(theID, node)
        }
    }
}
