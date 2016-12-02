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
package griffon.builder.trident.factory

import griffon.builder.trident.impl.MutableTimelineRunnable
import org.pushingpixels.trident.Timeline
import org.pushingpixels.trident.TimelineRunnable

/**
 * @author Andres Almiray
 */
class TimelineRunnableFactory extends AbstractFactory {
    Object newInstance(FactoryBuilderSupport builder, Object name, Object value, Map attributes)
        throws InstantiationException, IllegalAccessException {
        if (value instanceof TimelineRunnable) {
            return value
        }
        return new MutableTimelineRunnable(builder)
    }

    boolean isHandlesNodeChildren() {
        return true
    }

    boolean onNodeChildren(FactoryBuilderSupport builder, Object node, Closure childContent) {
        node.closure = childContent
        return false
    }

    void setParent(FactoryBuilderSupport builder, Object parent, Object child) {
        if (parent instanceof Timeline) {
            parent.addCallback(child)
        }
    }
}
