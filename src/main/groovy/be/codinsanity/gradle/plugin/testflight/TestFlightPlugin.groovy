/*
 * Copyright 2013 Bruno Dusausoy
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */
package be.codinsanity.gradle.plugin.testflight

import com.android.build.gradle.AppExtension
import com.android.build.gradle.AppPlugin
import com.android.build.gradle.internal.dsl.GroupableProductFlavorDsl
import com.android.build.gradle.internal.dsl.GroupableProductFlavorFactory
import org.gradle.api.Project
import org.gradle.internal.reflect.Instantiator
import org.gradle.tooling.provider.model.ToolingModelBuilderRegistry

import javax.inject.Inject

/**
 * @author Bruno Dusausoy
 */
class TestFlightPlugin extends AppPlugin {

    AppExtension extension

    @Inject
    TestFlightPlugin(Instantiator instantiator, ToolingModelBuilderRegistry registry) {
        super(instantiator, registry)
    }

    @Override
    void apply(Project project) {
        super.apply(project)
        def productFlavorContainer = project.container(GroupableProductFlavorDsl,
                new GroupableProductFlavorFactory(instantiator, project.fileResolver))

        productFlavorContainer.whenObjectAdded { GroupableProductFlavorDsl productFlavor ->
            addUploadToTestFlightTasks(productFlavor)
        }

    }

    void addUploadToTestFlightTasks(GroupableProductFlavorDsl productFlavor) {
        def name = productFlavor.getName()

        def uploadTask = project.tasks.create("uploadToTestFlight${name}")
        uploadTask.description = "Upload ${name} build to TestFlight"
        uploadTask.group = "Deploy"
    }
}
