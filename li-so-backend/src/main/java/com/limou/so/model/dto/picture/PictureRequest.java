package com.limou.so.model.dto.picture;

import com.limou.so.common.PageRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.List;
@EqualsAndHashCode(callSuper = true)
@Data
public class PictureRequest extends PageRequest implements Serializable {
    /**
     * 搜索词
     */
    private String searchText;

    private static final long serialVersionUID = 1L;
}
