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
package org.codehaus.griffon.runtime.trident.swing

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
import griffon.core.GriffonApplication
import griffon.core.test.GriffonUnitRule
import griffon.util.BuilderCustomizer
import griffon.util.CompositeBuilder
import org.junit.Rule
import spock.lang.Specification
import spock.lang.Unroll

import javax.annotation.Nonnull
import javax.inject.Inject

import static griffon.util.AnnotationUtils.sortByDependencies

/**
 * @author Andres Almiray
 */
class TridentSwingGroovyModuleSpec extends Specification {
    static {
        System.setProperty('org.slf4j.simpleLogger.defaultLogLevel', 'trace')
    }

    @Rule
    public final GriffonUnitRule griffon = new GriffonUnitRule()

    @Inject
    private GriffonApplication application

    @Unroll
    def 'Builder customizer is configured correctly'() {
        when:
        FactoryBuilderSupport builder = createBuilder(application)

        then:
        builder.factories.containsKey(node)
        builder.factories[node].class == factory

        where:
        node                       | factory
        'timeline'                 | TimelineFactory
        'timelineCallback'         | TimelineCallbackFactory
        'uiThreadTimelineCallback' | UIThreadTimelineCallbackFactory
        'interpolatedProperty'     | InterpolatedPropertyFactory
        'keyFrames'                | KeyFramesFactory
        'keyFrame'                 | KeyFrameFactory
        'linearEase'               | EaseFactory
        'sineEase'                 | EaseFactory
        'splineEase'               | SplineEaseFactory
        'timelineScenario'         | TimelineScenarioFactory
        'parallelScenario'         | TimelineScenarioFactory
        'sequenceScenario'         | TimelineScenarioFactory
        'rendevouzScenario'        | TimelineScenarioFactory
        'timelineScenarioCallback' | TimelineScenarioCallbackFactory
        'swingRepaintTimeline'     | SwingRepaintTimelineFactory
        'timelineRunnable'         | TimelineRunnableFactory
        'actionTrigger'            | ActionTriggerFactory
        'mouseTrigger'             | MouseTriggerFactory
        'focusTrigger'             | FocusTriggerFactory
    }

    private static final String BUILDER_CUSTOMIZER = 'BuilderCustomizer'

    @Nonnull
    protected FactoryBuilderSupport createBuilder(@Nonnull GriffonApplication application) {
        Collection<BuilderCustomizer> customizers = resolveBuilderCustomizers(application)
        return new CompositeBuilder(customizers.toArray(new BuilderCustomizer[customizers.size()]))
    }

    @Nonnull
    private Collection<BuilderCustomizer> resolveBuilderCustomizers(@Nonnull GriffonApplication application) {
        Collection<BuilderCustomizer> customizerInstances = application.injector.getInstances(BuilderCustomizer)
        return sortByDependencies(customizerInstances, BUILDER_CUSTOMIZER, 'customizer').values()
    }
}
