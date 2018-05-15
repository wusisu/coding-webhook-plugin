/**
 * Jenkins plugin for Coding https://coding.net
 *
 * Copyright (c) 2016-2018 Shuanglei Tao <tsl0922@gmail.com>
 * Copyright (c) 2016-present, Coding, Inc.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package net.coding.jenkins.plugin.oauth;

import hudson.Extension;
import hudson.model.UnprotectedRootAction;
import hudson.security.SecurityRealm;
import jenkins.model.Jenkins;
import org.kohsuke.accmod.Restricted;
import org.kohsuke.accmod.restrictions.NoExternalUse;

/**
 * A page that shows a simple message when the user logs out.
 * This prevents a logout -> login loop when using this security realm and Anonymous does not have {@code Overall.READ} permission.
 */
@Extension
public class CodingLogoutAction implements UnprotectedRootAction {

    /** The URL of the action. */
    static final String POST_LOGOUT_URL = "githubLogout";

    @Override
    public String getDisplayName() {
        return "Github Logout";
    }

    @Override
    public String getIconFileName() {
        // hide it
        return null;
    }

    @Override
    public String getUrlName() {
        return POST_LOGOUT_URL;
    }

    @Restricted(NoExternalUse.class) // jelly only
    public String getGitHubURL() {
        Jenkins j = Jenkins.getInstance();
        assert j != null;
        SecurityRealm r = j.getSecurityRealm();
        if (r instanceof CodingSecurityRealm) {
            CodingSecurityRealm ghsr = (CodingSecurityRealm) r;
            return ghsr.getGithubWebUri();
        }
        // only called from the Jelly if the CodingSecurityRealm is set...
        return "";
    }

    @Restricted(NoExternalUse.class) // jelly only
    public String getGitHubText() {
        Jenkins j = Jenkins.getInstance();
        assert j != null;
        SecurityRealm r = j.getSecurityRealm();
        if (r instanceof CodingSecurityRealm) {
            CodingSecurityRealm ghsr = (CodingSecurityRealm) r;
            return (ghsr.getDescriptor().getDefaultGithubWebUri().equals(ghsr.getGithubWebUri()))? "GitHub" : "GitHub Enterprise";
        }
        // only called from the Jelly if the CodingSecurityRealm is set...
        return "";
    }
}
