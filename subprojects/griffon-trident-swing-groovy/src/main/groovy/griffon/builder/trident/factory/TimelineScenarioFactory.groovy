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

import org.pushingpixels.trident.Timeline
import org.pushingpixels.trident.TimelineRunnable
import org.pushingpixels.trident.callback.TimelineScenarioCallback

/**
 * @author Andres Almiray
 */
class TimelineScenarioFactory extends AbstractFactory {
    private final scenarioClass

    TimelineScenarioFactory(Class scenarioClass) {
        this.scenarioClass = scenarioClass
    }

    Object newInstance(FactoryBuilderSupport builder, Object name, Object value, Map attributes)
        throws InstantiationException, IllegalAccessException {
        if (value != null && scenarioClass.isAssignableFrom(value.class)) {
            return value
        }

        FactoryBuilderSupport.checkValueIsNull(value, name)
        return scenarioClass.newInstance()
    }

    void setChild(FactoryBuilderSupport builder, Object parent, Object child) {
        if (child instanceof Timeline || child instanceof TimelineRunnable) {
            parent.addScenarioActor(child)
        } else if (child instanceof TimelineScenarioCallback) {
            parent.addCallback(child)
        }
    }
}
