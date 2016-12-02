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
package griffon.builder.trident.impl

import org.pushingpixels.trident.callback.TimelineScenarioCallback

/**
 * @author Andres Almiray
 */
class MutableTimelineScenarioCallback implements TimelineScenarioCallback {
    Closure onDoneClosure

    private final builder

    MutableTimelineScenarioCallback(FactoryBuilderSupport builder) {
        this.builder = builder
    }

    public void onTimelineScenarioDone() {
        execClosure(onDoneClosure)
    }

    public void setOnDone(Closure closure) {
        onDone(closure)
    }

    public void onDone(Closure closure) {
        onDoneClosure = closure
    }

    // =====================

    private closureDelegate
    Closure closure
    private boolean initialized

    private void init() {
        synchronized (this) {
            if (initialized) return
        }
        initialized = true
        closureDelegate = closure.delegate
        closure.resolveStrategy = Closure.DELEGATE_FIRST
        closure.delegate = this
        closure()
    }

    private void execClosure(Closure cls) {
        init()
        if (!cls) return
        cls.resolveStrategy = Closure.DELEGATE_FIRST
        cls.delegate = this
        cls()
    }

    def methodMissing(String name, Object value) {
        // try the builder first
        try {
            callPropertyOrMethod(builder, name, value)
        } catch (MissingMethodException mme) {
            callPropertyOrMethod(closureDelegate, name, value)
        }
    }

    def callPropertyOrMethod(target, name, args) {
        try {
            def cls = target.getProperty(name)
            if (args?.getClass()?.isArray() || args instanceof List) {
                return cls(*args)
            } else {
                return cls(args)
            }
        } catch (MissingPropertyException mpe) {
            if (args?.getClass()?.isArray() || args instanceof List) {
                return target."$name"(*args)
            } else {
                return target."$name"(args)
            }
        }
    }

    def propertyMissing(String name) {
        // try the builder first
        try {
            return builder.getProperty(name)
        } catch (MissingPropertyException mpe) {
            return closureDelegate."$name"
        }
    }

    def propertyMissing(String name, Object value) {
        // try the builder first
        try {
            builder.setProperty(name, value)
        } catch (MissingMethodException mpe) {
            closureDelegate."$name" = value
        }
    }
}