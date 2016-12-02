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
 * Superclass for all TriggerEvents used in the Trigger classes. The methods
 * here are mostly protected; it is expected that callers will not use this
 * class directly, but will instead use subclasses with pre-defined event types.
 * The purpose of this superclass is to provide the ability for {@link Trigger}
 * to treat event types generically, rather than to have all even logic in the
 * subclasses of Trigger.
 *
 * @author Chet
 * @author R�my Rakic - toString method using the unused name
 */
public class TriggerEvent {
    /**
     * The ID of events are simple strings. It is expected that subclasses will
     * define static objects that callers will use instead of users having to
     * manually create TriggerEvent objects from strings directly
     */
    private String name;

    /**
     * Protected constructor; this helps ensure type-safe use of pre-defined
     * TriggerEvent objects.
     */
    protected TriggerEvent(String name) {
        this.name = name;
    }

    /**
     * This method returns the 'opposite' event from itself. This is used by
     * {@link Trigger} in running an auto-reversing animation, to determine
     * whether an opposite event has occurred (and whether to stop/reverse the
     * animation). Note that some events may have no opposite. Default behavior
     * returns same event; subclasses with multiple/opposite events must
     * override to do the right thing here.
     */
    public TriggerEvent getOppositeEvent() {
        return this;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(getClass().getSimpleName()).append(".").append(name);
        TriggerEvent oppositeEvent = getOppositeEvent();
        if (oppositeEvent != this) {
            builder.append(" [opposite event: ").append(oppositeEvent.getClass().getSimpleName())
                .append(".").append(oppositeEvent.name).append("]");
        }
        return builder.toString();
    }
}