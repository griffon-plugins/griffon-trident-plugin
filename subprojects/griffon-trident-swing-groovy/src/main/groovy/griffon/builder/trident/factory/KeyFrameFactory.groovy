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

import griffon.builder.trident.impl.KeyFrame
import org.pushingpixels.trident.ease.Linear
import org.pushingpixels.trident.ease.TimelineEase

/**
 * @author Andres Almiray
 */
class KeyFrameFactory extends AbstractFactory {
    Object newInstance(FactoryBuilderSupport builder, Object name, Object value, Map attributes)
        throws InstantiationException, IllegalAccessException {
        FactoryBuilderSupport.checkValueIsNull(value, name)
        def offset = attributes.remove("offset") ?: 0.0f
        value = attributes.remove("value")
        TimelineEase ease = attributes.remove("ease") ?: new Linear()
        if (offset == null) {
            throw new RuntimeException("In $name you must specify a value for the offset: attribute.")
        }
        return new KeyFrame(offset, value, ease)
    }
}
