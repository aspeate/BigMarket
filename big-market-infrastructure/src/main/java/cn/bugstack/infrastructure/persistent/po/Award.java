package cn.bugstack.infrastructure.persistent.po;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Award {

    /** 主键id */
    private Long id;
    /** 抽奖策略id */
    private Integer awardId;
    /** 奖品对接标识 - 每一个都是一个对应的发奖策略 */
    private String awardKey;
    /** 奖品配置信息 */
    private String awardConfig;
    /** 奖品描述 */
    private String awardDesc;
    /** 创建时间 */
    private Date createTime;
    /** 更改时间 */
    private Date updateTime;
}

