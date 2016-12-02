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

import griffon.builder.trident.impl.InterpolatedProperty
import griffon.builder.trident.impl.KeyFrame
import org.pushingpixels.trident.interpolator.KeyFrames
import org.pushingpixels.trident.interpolator.KeyTimes
import org.pushingpixels.trident.interpolator.KeyValues
import org.pushingpixels.trident.interpolator.PropertyInterpolator

/**
 * @author Andres Almiray
 */
class KeyFramesFactory extends AbstractFactory {
    Object newInstance(FactoryBuilderSupport builder, Object name, Object value, Map attributes)
        throws InstantiationException, IllegalAccessException {
        PropertyInterpolator interpolator = attributes.remove("interpolator")
        [interpolator: interpolator, keyframes: []]
    }

    void setChild(FactoryBuilderSupport builder, Object parent, Object child) {
        if (child instanceof KeyFrame) parent.keyframes << child
    }

    void onNodeCompleted(FactoryBuilderSupport builder, Object parent, Object node) {
        if (parent instanceof InterpolatedProperty) {
            def offsets = []
            def values = []
            def eases = []
            node.keyframes.each {
                offsets << it.offset
                values << it.value
                eases << it.ease
            }
            // chop the last ease
            eases = eases[0..-2]

            KeyValues keyValues = node.interpolator ? KeyValues.create(node.interpolator, *values) : KeyValues.create(*values)
            KeyFrames keyFrames = eases ? new KeyFrames(keyValues, new KeyTimes(*offsets), *eases) :
                new KeyFrames(keyValues, new KeyTimes(*offsets))
            parent.keyFrames = keyFrames
        }
    }
}