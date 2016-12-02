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
 * Focus In/Out events
 *
 * @author Chet
 * @author R�my Rakic - Modifications for Trident + renaming the IN/OUT events to the more familiar GAINED/LOST
 */
public class FocusTriggerEvent extends TriggerEvent {
    /**
     * Event fired when Component receives focus
     */
    public static final FocusTriggerEvent GAINED = new FocusTriggerEvent("FocusGained");
    /**
     * Event fired when Component loses focus
     */
    public static final FocusTriggerEvent LOST = new FocusTriggerEvent("FocusLost");

    /**
     * Private constructor; this helps ensure type-safe use of pre-defined
     * TriggerEvent objects.
     */
    private FocusTriggerEvent(String name) {
        super(name);
    }

    /**
     * This method finds the opposite of the current event.: GAINED -> LOST and LOST
     * -> GAINED.
     */
    public TriggerEvent getOppositeEvent() {
        if (this == FocusTriggerEvent.GAINED) {
            return FocusTriggerEvent.LOST;
        } else {
            return FocusTriggerEvent.GAINED;
        }
    }
};