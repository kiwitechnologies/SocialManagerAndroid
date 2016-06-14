/*
 * Copyright (c) 2016 Kiwitech
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package com.tsg.kiwitech.tsgsocialsharing.linkedInUtility;

/**
 * Created by kiwitech on 10/06/16.
 */

public class Constants {

    public final static String URL_GET_PROFILE = "https://api.linkedin.com/v1/people/~?format=json";
    public final static String URL_GET_PROFILE_ADDITIONAL_INFO = "https://api.linkedin.com/v1/people/~:(id,num-connections,picture-url)?format=json";
    public final static String URL_GET_COMPANY_INFO = "https://api.linkedin.com/v1/companies/1337:(id,name,ticker,description)?format=json";
    public static String URL_SHARE_POST = "https://api.linkedin.com/v1/people/~/shares?format=json";
}
