package org.harbor.client.client.op;

import org.harbor.client.client.HarborResponse;
import org.harbor.client.client.model.ListFilter;
import org.harbor.client.client.model.Tag;

import java.util.List;

/**
 * @author lr
 * @date 2021/2/26
 */
public interface Tags {
    List<Tag> list(ListFilter filter);

    HarborResponse create(Tag tag);

    HarborResponse delete(String tagName);
}
