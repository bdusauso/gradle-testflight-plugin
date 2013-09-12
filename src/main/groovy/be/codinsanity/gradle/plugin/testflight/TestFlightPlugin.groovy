package be.codinsanity.gradle.plugin.testflight

import org.gradle.api.Plugin
import org.gradle.api.Project

/**
 * @author Bruno Dusausoy
 */
class TestFlightPlugin implements Plugin<Project> {

    @Override
    void apply(Project project) {
        project.plugins.apply('android')
    }
}
