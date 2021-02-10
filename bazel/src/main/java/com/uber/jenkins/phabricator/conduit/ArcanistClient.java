// Copyright (c) 2015 Uber Technologies, Inc.
//
// Permission is hereby granted, free of charge, to any person obtaining a copy
// of this software and associated documentation files (the "Software"), to deal
// in the Software without restriction, including without limitation the rights
// to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
// copies of the Software, and to permit persons to whom the Software is
// furnished to do so, subject to the following conditions:
//
// The above copyright notice and this permission notice shall be included in
// all copies or substantial portions of the Software.
//
// THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
// IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
// FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
// AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
// LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
// OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
// THE SOFTWARE.

package com.uber.jenkins.phabricator.conduit;

import cli.RunCommand;
import com.uber.jenkins.phabricator.utils.CommonUtils;

import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ArcanistClient {

    private final String arcPath;
    private final String methodName;
    private final String conduitUrl;
    private final String conduitToken;
    private final String[] arguments;

    public ArcanistClient(
            String arcPath,
            String methodName,
            String conduitUrl,
            String conduitToken,
            String... arguments) {
        this.arcPath = arcPath;
        this.methodName = methodName;
        this.conduitUrl = conduitUrl;
        this.conduitToken = conduitToken;
        this.arguments = arguments;
    }

    private List<String> getConduitCommand() {
        List<String> builder = new ArrayList<String>();
        builder.add(this.arcPath);
        builder.add(this.methodName);
        builder.addAll(Arrays.asList(arguments));

        if (!CommonUtils.isBlank(this.conduitUrl)) {
            builder.add("--conduit-uri=" + this.conduitUrl);
        }

        if (!CommonUtils.isBlank(this.conduitToken)) {
            builder.add("--conduit-token=" + this.conduitToken);
        }
        return builder;
    }

    private List<String> getCommand() {
        return this.getConduitCommand();
    }

    public void callConduit() {
        String output = RunCommand.run(getCommand(), "");
        System.out.println(output);
    }
}
