package org.apache.archiva.repository.maven2;

import org.apache.archiva.repository.AbstractRemoteRepository;
import org.apache.archiva.repository.ReleaseScheme;
import org.apache.archiva.repository.RemoteRepository;
import org.apache.archiva.repository.RepositoryCapabilities;
import org.apache.archiva.repository.RepositoryType;
import org.apache.archiva.repository.StandardCapabilities;
import org.apache.archiva.repository.UnsupportedFeatureException;
import org.apache.archiva.repository.features.IndexCreationFeature;
import org.apache.archiva.repository.features.RemoteIndexFeature;
import org.apache.archiva.repository.features.RepositoryFeature;

import java.nio.file.Path;
import java.util.Locale;

/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

/**
 * Maven2 remote repository implementation
 */
public class MavenRemoteRepository extends AbstractRemoteRepository
    implements RemoteRepository
{
    final private RemoteIndexFeature remoteIndexFeature = new RemoteIndexFeature();
    final private IndexCreationFeature indexCreationFeature = new IndexCreationFeature(  );

    private static final RepositoryCapabilities CAPABILITIES = new StandardCapabilities(
        new ReleaseScheme[] { ReleaseScheme.RELEASE, ReleaseScheme.SNAPSHOT },
        new String[] { MavenManagedRepository.DEFAULT_LAYOUT, MavenManagedRepository.LEGACY_LAYOUT},
        new String[] {},
        new String[] {RemoteIndexFeature.class.getName(), IndexCreationFeature.class.getName()},
        true,
        true,
        true,
        true,
        false
    );

    public MavenRemoteRepository( String id, String name, Path basePath )
    {
        super( RepositoryType.MAVEN, id, name, basePath );
    }

    public MavenRemoteRepository( Locale primaryLocale, String id, String name, Path basePath )
    {
        super( primaryLocale, RepositoryType.MAVEN, id, name, basePath );
    }

    @Override
    public boolean hasIndex( )
    {
        return remoteIndexFeature.hasIndex();
    }

    @Override
    public RepositoryCapabilities getCapabilities( )
    {
        return CAPABILITIES;
    }

    @Override
    public <T extends RepositoryFeature<T>> RepositoryFeature<T> getFeature( Class<T> clazz ) throws UnsupportedFeatureException
    {
        if (RemoteIndexFeature.class.equals( clazz )) {
            return (RepositoryFeature<T>) remoteIndexFeature;
        } else if (IndexCreationFeature.class.equals(clazz)) {
            return (RepositoryFeature<T>) indexCreationFeature;
        } else {
            throw new UnsupportedFeatureException(  );
        }
    }

    @Override
    public <T extends RepositoryFeature<T>> boolean supportsFeature( Class<T> clazz )
    {
        if ( RemoteIndexFeature.class.equals(clazz) || IndexCreationFeature.class.equals(clazz)) {
            return true;
        } else {
            return false;
        }
    }
}
