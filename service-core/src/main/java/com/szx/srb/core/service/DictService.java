package com.szx.srb.core.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.szx.srb.core.pojo.dto.ExcelDictDTO;
import com.szx.srb.core.pojo.entity.Dict;

import java.io.InputStream;
import java.util.List;

/**
 * <p>
 * 数据字典 服务类
 * </p>
 *
 * @author szx
 * @since 2021-06-25
 */
public interface DictService extends IService<Dict> {
    void importData(InputStream inputStream);

    List<ExcelDictDTO> listDictData();

    List<Dict> listByPatentId(Long parentId);
}
