package org.apache.maven.repository.indexing;

/*
 * Copyright 2001-2005 The Apache Software Foundation.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import org.apache.maven.artifact.factory.ArtifactFactory;
import org.apache.maven.artifact.repository.ArtifactRepository;
import org.apache.maven.repository.digest.Digester;

/**
 * @author Edwin Punzalan
 * @plexus.component role="org.apache.maven.repository.indexing.RepositoryIndexingFactory"
 */
public class DefaultRepositoryIndexingFactory
    implements RepositoryIndexingFactory
{
    /**
     * @plexus.requirement
     */
    private Digester digester;

    /**
     * @plexus.requirement
     */
    private ArtifactFactory artifactFactory;

    /**
     * @see RepositoryIndexingFactory#createArtifactRepositoryIndexSearcher(ArtifactRepositoryIndex)
     */
    public ArtifactRepositoryIndexSearcher createArtifactRepositoryIndexSearcher( ArtifactRepositoryIndex index )
    {
        return new ArtifactRepositoryIndexSearcher( index, artifactFactory );
    }

    /**
     * @see RepositoryIndexingFactory#createArtifactRepositoryIndex(String, org.apache.maven.artifact.repository.ArtifactRepository)
     */
    public ArtifactRepositoryIndex createArtifactRepositoryIndex( String indexPath, ArtifactRepository repository )
        throws RepositoryIndexException
    {
        return new ArtifactRepositoryIndex( indexPath, repository, digester );
    }

    /**
     * @see RepositoryIndexingFactory#createPomRepositoryIndex(String, org.apache.maven.artifact.repository.ArtifactRepository)
     */
    public PomRepositoryIndex createPomRepositoryIndex( String indexPath, ArtifactRepository repository )
        throws RepositoryIndexException
    {
        return new PomRepositoryIndex( indexPath, repository, digester, artifactFactory );
    }

    /**
     * @see RepositoryIndexingFactory#createPomRepositoryIndexSearcher(PomRepositoryIndex) 
     */
    public PomRepositoryIndexSearcher createPomRepositoryIndexSearcher( PomRepositoryIndex index )
    {
        return new PomRepositoryIndexSearcher( index, artifactFactory );
    }
}
