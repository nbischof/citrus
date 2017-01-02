/*
 * Copyright 2006-2016 the original author or authors.
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

package com.consol.citrus.selenium.actions;

import com.consol.citrus.context.TestContext;
import com.consol.citrus.selenium.endpoint.SeleniumBrowser;
import org.openqa.selenium.WebElement;

/**
 * @author Tamer Erdogan, Christoph Deppisch
 * @since 2.7
 */
public class CheckInputAction extends FindElementAction {

    private boolean checked;

    /**
     * Default constructor.
     */
    public CheckInputAction() {
        super("check-input");
    }

    @Override
    protected void execute(WebElement webElement, SeleniumBrowser browser, TestContext context) {
        super.execute(webElement, browser, context);

        if (webElement.isSelected() && !checked) {
            webElement.click();
        } else if (checked && !webElement.isSelected()) {
            webElement.click();
        }
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public boolean isChecked() {
        return checked;
    }
}
