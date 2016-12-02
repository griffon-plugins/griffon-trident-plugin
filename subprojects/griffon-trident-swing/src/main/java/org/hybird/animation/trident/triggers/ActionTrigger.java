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
package org.hybird.animation.trident.triggers;

import org.pushingpixels.trident.Timeline;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Method;

/**
 * ActionTrigger handles action events and starts the timeline when actions
 * occur. For example, to have anim start when a button is clicked, one might
 * write the following:
 * <p>
 * <pre>
 * ActionTrigger trigger = ActionTrigger.addTrigger (button, anim);
 * </pre>
 *
 * @author Chet
 * @author R�my Rakic - Modifications for Trident and javadocs
 */
public class ActionTrigger extends Trigger implements ActionListener {

    /**
     * Creates an ActionTrigger and adds it as a listener to object.
     *
     * @param object   an object that will be used as an event source for this
     *                 trigger. This object must have the method addActionListener.
     * @param timeline the Timeline that will start when the event occurs
     *
     * @return ActionTrigger the resulting trigger
     *
     * @throws IllegalArgumentException if object has no <code>addActionListener()</code>
     */
    public static ActionTrigger addTrigger(Object object, Timeline timeline) {
        ActionTrigger trigger = new ActionTrigger(timeline);
        try {
            Method addListenerMethod = object.getClass().getMethod("addActionListener", ActionListener.class);
            addListenerMethod.invoke(object, trigger);
        } catch (Exception e) {
            throw new IllegalArgumentException("Problem adding listener to object: " + e);
        }
        return trigger;
    }

    /**
     * Creates an ActionTrigger that will start the timeline upon receiving any
     * ActionEvents. It should be added to any suitable object with an
     * addActionListener method.
     *
     * @param timeline the Timeline that will start when the event occurs
     */
    public ActionTrigger(Timeline timeline) {
        super(timeline);
    }

    /**
     * Called by an object generating ActionEvents to which this trigger was
     * added as an ActionListener. This starts the Timeline.
     */
    public void actionPerformed(ActionEvent ae) {
        fire();
    }
}