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

/**
 * Mouse Enter/Exit/Press/Release/Click events
 *
 * @author Chet
 */
public class MouseTriggerEvent extends TriggerEvent {
    /**
     * Event fired when mouse enters
     */
    public static final MouseTriggerEvent ENTER = new MouseTriggerEvent("Entered");
    /**
     * Event fired when mouse exits
     */
    public static final MouseTriggerEvent EXIT = new MouseTriggerEvent("Exit");
    /**
     * Event fired when mouse button is pressed
     */
    public static final MouseTriggerEvent PRESS = new MouseTriggerEvent("Press");
    /**
     * Event fired when mouse button is released
     */
    public static final MouseTriggerEvent RELEASE = new MouseTriggerEvent("Release");
    /**
     * Event fired when mouse is clicked
     */
    public static final MouseTriggerEvent CLICK = new MouseTriggerEvent("Click");

    /**
     * Protected constructor; this helps ensure type-safe use of pre-defined
     * TriggerEvent objects.
     */
    private MouseTriggerEvent(String name) {
        super(name);
    }

    /**
     * This method finds the opposite of the current event.: <BR/>
     * ENTER -> EXIT <BR/>
     * EXIT -> ENTER <BR/>
     * PRESS -> RELEASE <BR/>
     * RELEASE -> PRESS <BR/>
     * Note that CLICK has no obvious opposite so it simply returns CLICK (this
     * method should probably not be called for that case).
     */
    public TriggerEvent getOppositeEvent() {
        if (this == MouseTriggerEvent.ENTER) {
            return MouseTriggerEvent.EXIT;
        } else if (this == MouseTriggerEvent.EXIT) {
            return MouseTriggerEvent.ENTER;
        } else if (this == MouseTriggerEvent.PRESS) {
            return MouseTriggerEvent.RELEASE;
        } else if (this == MouseTriggerEvent.RELEASE) {
            return MouseTriggerEvent.PRESS;
        }
        // Possible to reach here for REPEAT action (but probably should not
        // have been called with this event)
        return this;
    }
}