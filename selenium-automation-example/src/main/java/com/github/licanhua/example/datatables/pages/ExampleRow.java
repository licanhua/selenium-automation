/*
 *
 *  * Copyright (C) 2016 The Selenium Automation Framework Authors
 *  *
 *  * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 *  * in compliance with the License. You may obtain a copy of the License at
 *  *
 *  * http://www.apache.org/licenses/LICENSE-2.0
 *  *
 *  * Unless required by applicable law or agreed to in writing, software distributed under the License
 *  * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 *  * or implied. See the License for the specific language governing permissions and limitations under
 *  * the License.
 *  *
 *  * Created by canhua li (licanhua@live.com)
 *  *
 *
 */

package com.github.licanhua.example.datatables.pages;

import com.github.licanhua.test.framework.Container;
import com.github.licanhua.test.framework.annotation.AutoValidation;
import com.github.licanhua.test.framework.annotation.RelativeElement;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * @author Canhua Li
 */
@AutoValidation(false)
public class ExampleRow extends Container {
    @RelativeElement
    @FindBy(xpath="./../td[2]")
    WebElement postition;

    @RelativeElement
    @FindBy(xpath = "./../td[4]")
    WebElement office;

    public void print() {
        System.out.print("Position " + postition.getText() + " office" + office);
    }
}
