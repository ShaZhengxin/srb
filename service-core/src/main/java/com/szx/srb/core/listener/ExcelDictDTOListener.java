package com.szx.srb.core.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.szx.srb.core.mapper.DictMapper;
import com.szx.srb.core.pojo.dto.ExcelDictDTO;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

/**
 * @author szx
 * @description TODO
 * @date 2021/7/12  22:44
 */
@Slf4j
@NoArgsConstructor
public class ExcelDictDTOListener extends AnalysisEventListener<ExcelDictDTO> {
    /**数据列表*/
    List<ExcelDictDTO> list= new ArrayList<ExcelDictDTO>();
    /**没隔5条记录批量存储一次数据（实际生产经验值为3000）*/
    private static final int BATCH_COUNT=5;
    private DictMapper dictMapper;

    public ExcelDictDTOListener(DictMapper dictMapper){
        this.dictMapper=dictMapper;
    }

    @Override
    public void invoke(ExcelDictDTO data, AnalysisContext context) {
        log.info("解析到一条记录"+data);
        //将数据存入数据列表
        list.add(data);
        if(list.size()>=BATCH_COUNT){
            saveData();
            list.clear();
        }
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        /**当最后剩余的数据不足BATCH_COUNT，最后一次性存储剩余数据*/
        saveData();
        log.info("所有数据解析完成！");
    }

    private void saveData(){
        log.info("{}条数据存储到数据库.......",list.size());
        //调用mapper层的save方法
        dictMapper.insertBatch(list);
        log.info("{}条数据存储到数据库成功",list.size());
    }
}
