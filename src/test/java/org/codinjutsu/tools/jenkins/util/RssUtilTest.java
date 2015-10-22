/*
 * Copyright (c) 2013 David Boissier
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

package org.codinjutsu.tools.jenkins.util;

import org.codinjutsu.tools.jenkins.model.BuildStatusEnum;
import org.hamcrest.core.IsEqual;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

@RunWith(Parameterized.class)
public class RssUtilTest {

    private final String inputRssTitle;
    private final BuildStatusEnum expectedStatus;
    private final String expectedBuildNumber;
    private final String expectedJobName;

    @Parameterized.Parameters
    public static Collection testData() {
        return Arrays.asList(new Object[][]{
                {"gerrit_master #170 (broken since build #165)", BuildStatusEnum.FAILURE, "170", "gerrit_master"},
                {"hudson_metrics_wip #6 (aborted)", BuildStatusEnum.ABORTED, "6", "hudson_metrics_wip"},
                {"infa_release.rss #139 (stable)", BuildStatusEnum.SUCCESS, "139", "infa_release.rss"},
                {"infra_jenkins-ci.org_webcontents #2 (back to normal)", BuildStatusEnum.SUCCESS, "2", "infra_jenkins-ci.org_webcontents"},
                {"jenkins_main_trunk #600 (?)", BuildStatusEnum.NULL, "600", "jenkins_main_trunk"},
                {"plugins_subversion #58 (2 tests are still failing)", BuildStatusEnum.FAILURE, "58", "plugins_subversion"},
                {"TESTING-HUDSON-7434 #2 (broken for a long time)", BuildStatusEnum.FAILURE, "2", "TESTING-HUDSON-7434"},
        });
    }

    public RssUtilTest(String inputRssTitle, BuildStatusEnum expectedStatus, String expectedBuildNumber, String expectedJobName) {
        this.inputRssTitle = inputRssTitle;
        this.expectedStatus = expectedStatus;
        this.expectedBuildNumber = expectedBuildNumber;
        this.expectedJobName = expectedJobName;
    }

    @Test
    public void shouldExtractData() throws Exception {
        Assert.assertThat(RssUtil.extractStatus(inputRssTitle), IsEqual.equalTo(expectedStatus));
        Assert.assertThat(RssUtil.extractBuildNumber(inputRssTitle), IsEqual.equalTo(expectedBuildNumber));
        Assert.assertThat(RssUtil.extractBuildJob(inputRssTitle), IsEqual.equalTo(expectedJobName));
    }
}
