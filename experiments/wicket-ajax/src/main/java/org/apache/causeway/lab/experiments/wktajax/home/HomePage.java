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

package org.apache.causeway.lab.experiments.wktajax.home;

import java.util.Optional;

import com.giffing.wicket.spring.boot.context.scan.WicketHomePage;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxFallbackLink;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

import org.apache.causeway.lab.experiments.wktajax.sample.BasePage;

@WicketHomePage
public class HomePage extends BasePage {

    private static final long serialVersionUID = 1L;

    @Override
    protected void onInitialize() {
        super.onInitialize();
        System.err.printf("programmatic %s%n", "person");
        var person = new Person("hello", "world");

        var personPanel = new PersonPanel("personPanel", new PersonModel(person));

        add(personPanel);

        add(new AjaxFallbackLink<String>("link", Model.of("update")) {
            private static final long serialVersionUID = 1L;
            @Override
            public void onClick(final Optional<AjaxRequestTarget> target) {
                target.ifPresent(ajaxRequestTarget->
                    ajaxRequestTarget.add(personPanel));
                System.err.printf("%s%n", "update");
            }
        });
    }

    record PersonModel(Person person) implements IModel<Person> {

        @Override
        public Person getObject() {
            return person;
        }

        public IModel<String> first() {
            return this.map(Person::first);
        }

        public IModel<String> last() {
            return this.map(Person::last);
        }

    }

}
