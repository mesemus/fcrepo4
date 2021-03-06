/*
 * Copyright 2015 DuraSpace, Inc.
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
package org.fcrepo.kernel.modeshape.services;

import java.util.Map;

import javax.jcr.Session;

import org.fcrepo.kernel.api.services.NamespaceService;
import org.fcrepo.kernel.modeshape.utils.NamespaceTools;
import org.springframework.stereotype.Component;

/**
 * This implements a Namespace service that can be injected
 * into other layers of Fedora. It is used for extracting
 * a Map of namespaces (prefix to URI).
 *
 * @author acoburn
 * @since 5/20/16
 */
@Component
public class NamespaceServiceImpl implements NamespaceService {

    @Override
    public Map<String, String> getNamespaces(final Session session) {
        return NamespaceTools.getNamespaces(session);
    }
}
