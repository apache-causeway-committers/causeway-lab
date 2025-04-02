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

import java.util.List;
import java.util.Optional;

import com.giffing.wicket.spring.boot.context.scan.WicketHomePage;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxFallbackLink;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.model.Model;

import org.apache.causeway.lab.experiments.wktajax.sample.BasePage;

@WicketHomePage
public class HomePage extends BasePage {

    private static final long serialVersionUID = 1L;

    @Override
    protected void onInitialize() {
        super.onInitialize();
        System.err.printf("HomePage %s%n", "INIT");

        var personModel = new PersonModel();
        var personPanel = new PersonPanel("personPanel", personModel);

        add(personPanel);

        add(new AjaxFallbackLink<String>("link", Model.of("update")) {
            private static final long serialVersionUID = 1L;
            @Override
            public void onClick(final Optional<AjaxRequestTarget> target) {
                target.ifPresent(ajaxRequestTarget->
                    ajaxRequestTarget.add(personPanel));

                var person = personModel.person();
                person.setFirstName(person.getFirstName() + ".");
                System.err.printf("%s%n", "PersonPanel UPDATE (AJAX)");
            }
        });
    }

    final class PersonModel extends LoadableDetachableModel<Person> {
        private static final long serialVersionUID = 1L;

        private List<String> memento = List.of("hello", "world");

        public Person person() {
            return getObject();
        }

        @Override
        protected Person load() {
            System.err.printf("PersonModel LOAD %s%n", System.identityHashCode(this));
            var person = new Person(memento.get(0), memento.get(1));
            return person;
        }

        public IModel<String> firstName() {
            return this.map(Person::getFirstName);
        }

        public IModel<String> lastName() {
            return this.map(Person::getLastName);
        }

        @Override
        protected void onDetach() {
            System.err.printf("PersonModel DETACH [%d]%n", System.identityHashCode(this));
            this.memento = List.of(person().getFirstName(), person().getLastName());
        }

    }

}
