package com.wpy.base.cache;

import java.io.Serializable;

public class ScanPool implements Serializable{

    /**
     * @Fields serialVersionUID : TODO(��һ�仰�������������ʾʲô) 
     */
    private static final long serialVersionUID = -9117921544228636689L;


    private Object session ;
    //����ʱ��  
    private Long createTime = System.currentTimeMillis();  

    //��¼״̬  
    private boolean scanFlag = false;  

    public boolean isScan(){  
        return scanFlag;  
    }  

    public void setScan(boolean scanFlag){  
        this.scanFlag = scanFlag; 
    } 

    /** 
     * ��ȡɨ��״̬�������û��ɨ�裬��ȴ��̶����� 
     * @param wiatSecond ��Ҫ�ȴ������� 
     * @return 
     */  
    public synchronized boolean getScanStatus(){  
        try  
        {  
            if(!isScan()){ //�����δɨ�裬��ȴ�  
                this.wait();  
            }  
            if (isScan())  
            {   System.err.println("�ֻ�ɨ���������getScanStatus..true...........");
                return true;  
            }  
        } catch (InterruptedException e)  
        {  
            e.printStackTrace();  
        }  
        return false;  
    }  

    /** 
     * ɨ��֮������ɨ��״̬ 
     * @param token 
     * @param id 
     */  
    public synchronized void scanSuccess(){  
        try  
        {  System.err.println("�ֻ�ɨ�����setScan(true)....ͬʱ�ͷ�notifyAll(�ֻ�ɨ��ʱ,����uuid��õ�scanpool����)");
            setScan(true); 
            this.notifyAll();  
        } catch (Exception e)  
        {  
            // TODO Auto-generated catch block  
            e.printStackTrace();  
        }  
    }  

    public synchronized void notifyPool(){  
        try  
        {  
            this.notifyAll();  
        } catch (Exception e)  
        {  
            // TODO Auto-generated catch block  
            e.printStackTrace();  
        }  
    }  

    /***********************************************/
    public Long getCreateTime()  
    {  
        return createTime;  
    }  

    public void setCreateTime(Long createTime)  
    {  
        this.createTime = createTime;  
    }

    public Object getSession() {
        return session;
    }

    public void setSession(Object session) {
        this.session = session;
    }



}