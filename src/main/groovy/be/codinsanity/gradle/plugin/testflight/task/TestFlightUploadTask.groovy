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
package be.codinsanity.gradle.plugin.testflight.task

import groovyx.net.http.HTTPBuilder
import groovyx.net.http.Method
import org.apache.http.entity.mime.MultipartEntity
import org.apache.http.entity.mime.content.FileBody
import org.apache.http.entity.mime.content.StringBody
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.TaskAction

class TestFlightUploadTask extends DefaultTask {

    static final String URL = 'http://testflightapp.com/api/builds.json'
    static final String CONTENT_TYPE = 'multipart/form-data'

    @Input String apkFilename
    @Input String apiToken
    @Input String teamToken
    @Input String notes
    @Input List<String> distributionLists
    @Input Boolean notify

    @TaskAction
    void upload() {
        def http = new HTTPBuilder(URL)

        http.request(Method.POST, CONTENT_TYPE) { req ->

            MultipartEntity entity = new MultipartEntity()
            entity.addPart('file', new FileBody(new File(apkFilename)))
            entity.addPart('api_token', new StringBody(apiToken))
            entity.addPart('team_token', new StringBody(teamToken))
            entity.addPart('notes', new StringBody(notes))
            entity.addPart('distribution_lists', new StringBody(distributionLists.join(',')))
            entity.addPart('notify', new StringBody(notify ? 'True' : 'False'))
            req.entity = entity
        }
    }
}