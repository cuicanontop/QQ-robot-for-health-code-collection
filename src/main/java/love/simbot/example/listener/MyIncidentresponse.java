package love.simbot.example.listener;

import cn.hutool.core.util.StrUtil;
import love.forte.common.ioc.annotation.Beans;
import love.forte.simbot.annotation.Filter;
import love.forte.simbot.annotation.OnFriendAddRequest;
import love.forte.simbot.annotation.OnGroupAddRequest;
import love.forte.simbot.api.message.containers.AccountInfo;
import love.forte.simbot.api.message.containers.BotInfo;
import love.forte.simbot.api.message.containers.GroupInfo;
import love.forte.simbot.api.message.events.FriendAddRequest;
import love.forte.simbot.api.message.events.GroupAddRequest;
import love.forte.simbot.api.sender.Setter;
import love.simbot.example.util.infolog;

@Beans
public class MyIncidentresponse {


    @OnFriendAddRequest
    public  void addIncidentMe (FriendAddRequest friendAddRequest,Setter setter){
        setter.acceptFriendAddRequest(friendAddRequest.getFlag());
        String txt = StrUtil.removeAllLineBreaks(friendAddRequest.getText().replaceAll(" +",""));

        infolog.LOGMyIncidentresponseinfo(friendAddRequest.getAccountInfo().getAccountNickname()+"("+friendAddRequest.getAccountInfo().getAccountCode()+")添加Bot："+friendAddRequest.getBotInfo().getAccountNickname()+"("+friendAddRequest.getBotInfo().getAccountCode()+") 理由："+txt+" 状态：已同意好友申请");
    }

    @OnGroupAddRequest
    public void onRequest(GroupAddRequest groupAddRequest, Setter setter) {
        AccountInfo accountInfo = groupAddRequest.getRequestAccountInfo();
        BotInfo botInfo = groupAddRequest.getBotInfo();
            if (accountInfo.getAccountCode().equals(botInfo.getBotCode())) {
            setter.acceptGroupAddRequest(groupAddRequest.getFlag());
            infolog.LOGMyIncidentresponseinfo(groupAddRequest.getInvitor().getInvitorNickname()+"("+groupAddRequest.getInvitor().getInvitorCode()+")邀请我加入群"+groupAddRequest.getGroupInfo().getGroupName()+"("+groupAddRequest.getGroupInfo().getGroupCode()+")已进入！");
        }

    }

}
