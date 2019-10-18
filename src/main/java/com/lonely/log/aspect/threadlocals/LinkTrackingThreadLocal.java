package com.lonely.log.aspect.threadlocals;

import com.lonely.log.aspect.bean.LinkTrackingBean;

/**
 * @author ztkj-hzb
 * @Date 2019/10/15 14:12
 * @Description 链路跟踪当前线程封装, 用于将数据信息根据当前线程服务传输下去
 */
public class LinkTrackingThreadLocal {


    private static final ThreadLocal<LinkTrackingBean> LINK_TRACKING_BEAN_THREAD_LOCAL = new ThreadLocal<>();

    /**
     * 获取当前线程中的链路对象
     *
     * @return
     */
    public static LinkTrackingBean get() {
        return LINK_TRACKING_BEAN_THREAD_LOCAL.get();
    }

    /**
     * 将指定链路对象设置到当前线程中
     *
     * @param linkTrackingBean
     */
    public static void set(LinkTrackingBean linkTrackingBean) {
        LINK_TRACKING_BEAN_THREAD_LOCAL.set(linkTrackingBean);
    }

    /**
     * 清除当前线程中的链路对象
     */
    public static void clear() {
        LINK_TRACKING_BEAN_THREAD_LOCAL.remove();
    }
}
