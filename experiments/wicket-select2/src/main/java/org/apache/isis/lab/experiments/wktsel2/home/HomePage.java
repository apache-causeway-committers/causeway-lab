/**
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */

package org.apache.isis.lab.experiments.wktsel2.home;

import com.giffing.wicket.spring.boot.context.scan.WicketHomePage;

import org.apache.isis.lab.experiments.wktsel2.sample.BasePage;

@WicketHomePage
public class HomePage extends BasePage {

    private static final long serialVersionUID = 1L;

//    public HomePage2() {
//	    add(new Label("hello", Model.of("hello select2")));
//	}
//
//    @Override
//    public void renderHead(final IHeaderResponse response) {
//        super.renderHead(response);
//
//        //response.render(PdfJsIntegrationReference.asHeaderItem());
//        //response.render(PdfJsIntegrationReference.domReadyScript(config));
//    }

}
