package org.harbor.client.client.op;

import org.harbor.client.client.model.ListFilter;
import org.harbor.client.client.model.Repository;

import java.util.List;

/**
 * @author lr
 * @date 2021/2/26
 */
public interface Repositories {
    // todo: 耗时
    List<Repository> list(ListFilter filter);

    RepositoryHandler repository(String repositoryName);
}
