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
package org.fcrepo.kernel.modeshape.rdf.impl;

import com.hp.hpl.jena.graph.Triple;
import com.hp.hpl.jena.rdf.model.Resource;
import org.fcrepo.kernel.api.models.FedoraResource;
import org.fcrepo.kernel.api.identifiers.IdentifierConverter;
import org.slf4j.Logger;

import javax.jcr.RepositoryException;

import static java.util.stream.Stream.of;
import static com.hp.hpl.jena.datatypes.xsd.XSDDatatype.XSDlong;
import static com.hp.hpl.jena.graph.Triple.create;
import static com.hp.hpl.jena.rdf.model.ResourceFactory.createTypedLiteral;
import static org.fcrepo.kernel.api.RdfLexicon.CONTAINS;
import static org.fcrepo.kernel.api.RdfLexicon.HAS_CHILD_COUNT;
import static org.fcrepo.kernel.modeshape.utils.FedoraTypesUtils.getJcrNode;
import static org.slf4j.LoggerFactory.getLogger;

/**
 * @author cabeer
 * @author ajs6f
 * @since 9/16/14
 */
public class ChildrenRdfContext extends NodeRdfContext {

    private static final Logger LOGGER = getLogger(ChildrenRdfContext.class);

    /**
     * Default constructor.
     *
     * @param resource the resource
     * @param idTranslator the idTranslator
     * @throws javax.jcr.RepositoryException if repository exception occurred
     */
    public ChildrenRdfContext(final FedoraResource resource,
                              final IdentifierConverter<Resource, FedoraResource> idTranslator)
            throws RepositoryException {
        super(resource, idTranslator);

        if (getJcrNode(resource).hasNodes()) {
            LOGGER.trace("Found children of this resource: {}", resource.getPath());

            // Count the number of children
            concat(of(createNumChildrenTriple(resource().getChildren().count())));
            concat(resource().getChildren().peek(child -> LOGGER.trace("Creating triple for child node: {}", child))
                    .map(child -> create(subject(), CONTAINS.asNode(), uriFor(child.getDescribedResource()))));
        } else {
            concat(of(createNumChildrenTriple(0)));
        }

    }

    private Triple createNumChildrenTriple(final long numChildren) {
        return create(subject(),
                HAS_CHILD_COUNT.asNode(),
                createTypedLiteral(Long.toString(numChildren), XSDlong).asNode());
    }

}
