/*
 * $Id: FreemarkerRequest.java 1332186 2012-04-30 13:20:15Z nlebas $
 *
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.apache.tiles.request.freemarker;

import java.io.PrintWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.tiles.request.AbstractViewRequest;
import org.apache.tiles.request.ApplicationContext;
import org.apache.tiles.request.DispatchRequest;
import org.apache.tiles.request.servlet.ServletRequest;

import freemarker.core.Environment;
import freemarker.ext.servlet.HttpRequestHashModel;

/**
 * The FreeMarker-specific request context.
 *
 * @version $Rev: 1332186 $ $Date: 2012-04-30 15:20:15 +0200 (Mon, 30 Apr 2012) $
 */
public class FreemarkerRequest extends AbstractViewRequest {

    /**
     * The natively available scopes. In fact, only "page".
     */
    private List<String> scopes;

    /**
     * The FreeMarker current environment.
     */
    private Environment env;

    /**
     * The page scope map.
     */
    private Map<String, Object> pageScope;

    /**
     * Creates a new Freemarker request.
     *
     * @param applicationContext The application context.
     * @param env The Freemarker's environment object.
     * @return A new request.
     */
    public static FreemarkerRequest createServletFreemarkerRequest(
            ApplicationContext applicationContext, Environment env) {
        HttpRequestHashModel requestModel = FreemarkerRequestUtil
                .getRequestHashModel(env);
        HttpServletRequest request = requestModel.getRequest();
        HttpServletResponse response = requestModel.getResponse();
        DispatchRequest enclosedRequest = new ServletRequest(
                applicationContext, request, response);
        return new FreemarkerRequest(enclosedRequest, env);
    }

    /**
     * Constructor.
     *
     * @param enclosedRequest
     *            The request that exposes non-FreeMarker specific properties
     * @param env
     *            The FreeMarker environment.
     */
    public FreemarkerRequest(DispatchRequest enclosedRequest,
            Environment env) {
        super(enclosedRequest);
        List<String> scopes = new ArrayList<String>();
        scopes.addAll(enclosedRequest.getAvailableScopes());
        scopes.add("page");
        this.scopes = Collections.unmodifiableList(scopes);
        this.env = env;
    }

    /**
     * Returns the environment object.
     *
     * @return The environment.
     */
    public Environment getEnvironment() {
        return env;
    }

    /** {@inheritDoc} */
    @Override
    public Locale getRequestLocale() {
        return env.getLocale();
    }

    /**
     * Returns the page scope.
     *
     * @return The page scope.
     */
    public Map<String, Object> getPageScope() {
        if (pageScope == null) {
            pageScope = new EnvironmentScopeMap(env);
        }
        return pageScope;
    }

    @Override
    public List<String> getAvailableScopes() {
        return scopes;
    }

    /** {@inheritDoc} */
    @Override
    public PrintWriter getPrintWriter() {
        Writer writer = env.getOut();
        if (writer instanceof PrintWriter) {
            return (PrintWriter) writer;
        }
        return new PrintWriter(writer);
    }

    /** {@inheritDoc} */
    @Override
    public Writer getWriter() {
        return env.getOut();
    }

    @Override
    public Map<String, Object> getContext(String scope) {
        return "page".equals(scope) ? getPageScope() : super.getContext(scope);
    }

}
