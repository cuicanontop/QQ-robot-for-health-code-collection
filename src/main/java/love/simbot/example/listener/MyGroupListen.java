package love.simbot.example.listener;

import catcode.CatCodeUtil;
import catcode.Neko;
import cn.hutool.core.util.NumberUtil;
import love.forte.common.ioc.annotation.Beans;
import love.forte.simbot.annotation.Filter;
import love.forte.simbot.annotation.OnGroup;
import love.forte.simbot.api.message.MessageContent;
import love.forte.simbot.api.message.events.GroupMsg;
import love.forte.simbot.api.sender.MsgSender;
import love.forte.simbot.filter.MatchType;
import love.simbot.example.Manager.ConfigManager;
import love.simbot.example.util.*;

import java.io.*;
import java.net.URL;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;


@Beans
public class MyGroupListen {
     public static final CatCodeUtil catUtil = CatCodeUtil.INSTANCE;
    public static String HAMSTRINGS = "璀璨健康码机器人菜单" +
            "\r\n直接发送健康进行收集" +
            "\r\n#获取 将当天图片打包发送" +
            "\r\n#信息 获取今天收集的健康码信息" +
            "\r\n#开启本群 开启本群处理信息" +
            "\r\n#开启本群 关闭本群处理信息" +
            "\r\n#是否提交 查询提交信息";

    public static String bug ="\r\nBUG提交QQ:1053067780";

    @OnGroup
    @Filter(value = "#获取", matchType = MatchType.EQUALS)
    public void Gzip(GroupMsg groupMsg, MsgSender sender) throws Exception{
        String cican =   H2Util.Gettableitem("qqid", NumberUtil.parseLong(groupMsg.getGroupInfo().getGroupCode()));
        if (cican==null||cican.equals("N"))return;
        String a = InTimer.getDate("yyyy-MM-dd");
        String s =System.getProperty("user.dir");
        String cat = "[CAT:at,code="+groupMsg.getAccountInfo().getAccountCode()+"]";
        File muluss = new File("健康码存储仓");
        File mulus = new File(muluss.getAbsolutePath()+"/"+ groupMsg.getGroupInfo().getGroupCode()+"\\"+a);
        File mulu = new File(muluss.getAbsolutePath()+"/"+ groupMsg.getGroupInfo().getGroupCode());
        if(!muluss.exists()) muluss.mkdir();
        if(!mulu.exists()) mulu.mkdir();
        if (!mulus.exists()){
            sender.SENDER.sendGroupMsg(groupMsg,cat+"今天还没有收到健康码\r\n日期:"+a+bug);
            return;
        }
        String nameArry[] = mulus.list();
        int i = 0;
        for (String eachName :nameArry){
            if(eachName.endsWith(".png")){
                i++;
            }
        }
        String name = groupMsg.getGroupInfo().getGroupName()+"健康码_"+a+"_人数-"+i+".zip";
        String file=s+"\\"+name;
        //要压缩的文件路径
        File dir = new File(mulus.getAbsolutePath());
        //压缩后zip包文件路径
        File dest = new File(file);
        OutputStream outputStream = new FileOutputStream(dest);
        ZipCompressing.zip(dir, outputStream);
        String fff = CatCodeUtil.getInstance().getStringCodeBuilder("file",true).key("file").value(file).key("path").value("//").key("fileName").value(name).build();
        sender.SENDER.sendGroupMsg(groupMsg,fff);
        dest.delete();
    }

    @OnGroup
    @Filter(value = "#信息", matchType = MatchType.EQUALS)
    public void onions(GroupMsg groupMsg, MsgSender sender) {
        String cican =   H2Util.Gettableitem("qqid", NumberUtil.parseLong(groupMsg.getGroupInfo().getGroupCode()));
        if (cican==null||cican.equals("N"))return;
        String a = InTimer.getDate("yyyy-MM-dd");
        AtomicInteger zif = new AtomicInteger();
        AtomicInteger zoning = new AtomicInteger();
        AtomicReference<String> txt = new AtomicReference<>("");
        String[] split = ConfigManager.getTeacheraccount().split("-");
        sender.GETTER.getGroupMemberList(groupMsg)
        .forEach(mub->{
            for (String inf :split) if (mub.getAccountCode().equals(inf)) return;
            zoning.getAndIncrement();
            String Ekrem = H2Util.Gettableitem(groupMsg.getGroupInfo().getGroupCode()+"jkm", NumberUtil.parseLong(mub.getAccountCode()));
            if(Ekrem==null||!Ekrem.equals(a)){
                zif.getAndIncrement();
                txt.set(txt + "[CAT:at,code=" + mub.getAccountCode() + "]");
            }
        });
        String msg = "健康码收集信息\r\n日期:"+a+"\r\n应收:"+zoning.toString()+"\r\n实收:"+(zoning.intValue()-zif.intValue())+"\r\n未交:"+zif.toString()+"\r\n名单:"+txt;
        sender.SENDER.sendGroupMsg(groupMsg,msg+bug);

    }


    @OnGroup
    @Filter(value = "#菜单", matchType = MatchType.EQUALS)
    public void onsen(GroupMsg groupMsg, MsgSender sender) {
        String cican =   H2Util.Gettableitem("qqid", NumberUtil.parseLong(groupMsg.getGroupInfo().getGroupCode()));
        if (cican==null||cican.equals("N"))return;
        String cat = "[CAT:at,code="+groupMsg.getAccountInfo().getAccountCode()+"]\r\n";
        sender.SENDER.sendGroupMsg(groupMsg,cat+ HAMSTRINGS +bug);

    }

    @OnGroup
    @Filter(value = "#是否提交", matchType = MatchType.EQUALS)
    public void onsite(GroupMsg groupMsg, MsgSender sender) {
        String cican =   H2Util.Gettableitem("qqid", NumberUtil.parseLong(groupMsg.getGroupInfo().getGroupCode()));
        if (cican==null||cican.equals("N"))return;
        String cat = "[CAT:at,code="+groupMsg.getAccountInfo().getAccountCode()+"]\r\n";
        String jkmrem = H2Util.Gettableitem(groupMsg.getGroupInfo().getGroupCode()+"jkm", NumberUtil.parseLong(groupMsg.getAccountInfo().getAccountCode()));
        if (jkmrem==null){
            cat = cat +"上次提交时间:null\r\n今天是否提交:否";
            sender.SENDER.sendGroupMsg(groupMsg,cat+bug);
            return;
        }
        String a = InTimer.getDate("yyyy-MM-dd");
        if (jkmrem.equals(a)){
            cat = cat +"上次提交时间:"+a+"\r\n今天是否提交:是";
            sender.SENDER.sendGroupMsg(groupMsg,cat+bug);

        }else {
            cat = cat +"上次提交时间:"+a+"\r\n今天是否提交:否";
            sender.SENDER.sendGroupMsg(groupMsg,cat+bug);
        }

    }

    @OnGroup
    @Filter(value = "#开启本群", matchType = MatchType.EQUALS)
    public void onY(GroupMsg groupMsg, MsgSender sender) {
        if(!groupMsg.getAccountInfo().getAccountCode().equals("1571330014")&&!groupMsg.getAccountInfo().getAccountCode().equals("1053067780")){
            sender.SENDER.sendGroupMsg(groupMsg,"你没有权限"+bug);
        }
        String cican =   H2Util.Gettableitem("qqid", NumberUtil.parseLong(groupMsg.getGroupInfo().getGroupCode()));
        if (cican==null){
            H2Util.Settableitem("qqid", NumberUtil.parseLong(groupMsg.getGroupInfo().getGroupCode()), "Y");
            sender.SENDER.sendGroupMsg(groupMsg,"本群已开启"+bug);
            return;
        }

        if (cican.equals("N")){
            H2Util.Settableitem("qqid", NumberUtil.parseLong(groupMsg.getGroupInfo().getGroupCode()), "Y");
            sender.SENDER.sendGroupMsg(groupMsg,"本群已开启"+bug);
        }else {
            if (cican.equals("Y")){
                sender.SENDER.sendGroupMsg(groupMsg,"本群已经为开启状态 无须再次开启"+bug);
            }
        }


    }

    @OnGroup
    @Filter(value = "#关闭本群", matchType = MatchType.EQUALS)
    public void onN(GroupMsg groupMsg, MsgSender sender) {
        if(!groupMsg.getAccountInfo().getAccountCode().equals("1571330014")&&!groupMsg.getAccountInfo().getAccountCode().equals("1053067780")){
            sender.SENDER.sendGroupMsg(groupMsg,"你没有权限"+bug);
        }
        String cican =   H2Util.Gettableitem("qqid", NumberUtil.parseLong(groupMsg.getGroupInfo().getGroupCode()));
        if (cican==null){
            H2Util.Settableitem("qqid", NumberUtil.parseLong(groupMsg.getGroupInfo().getGroupCode()), "N");
            sender.SENDER.sendGroupMsg(groupMsg,"本群已经为关闭状态 无须再次关闭"+bug);
            return;
        }
        if (cican.equals("Y")){
            H2Util.Settableitem("qqid", NumberUtil.parseLong(groupMsg.getGroupInfo().getGroupCode()), "N");
            sender.SENDER.sendGroupMsg(groupMsg,"本群已关闭"+bug);
        }else {
            if (cican.equals("N")){
                sender.SENDER.sendGroupMsg(groupMsg,"本群已经为关闭状态 无须再次关闭"+bug);
            }
        }


    }

    @OnGroup
    public void onGrouping(GroupMsg groupMsg, MsgSender sender) throws IOException {
        String cican =   H2Util.Gettableitem("qqid", NumberUtil.parseLong(groupMsg.getGroupInfo().getGroupCode()));
        if (cican==null||cican.equals("N"))return;
        infolog.Configinfo(groupMsg.getMsg()+"|来自群"+groupMsg.getGroupInfo().getGroupCode()+"—"+groupMsg.getGroupInfo().getGroupName()+"|发送人"+groupMsg.getAccountInfo().getAccountCode()+"_"+groupMsg.getAccountInfo().getAccountNickname()+"_"+groupMsg.getAccountInfo().getAccountRemark());
        MessageContent msgContent = groupMsg.getMsgContent();
        List<Neko> imageCats = msgContent.getCats("image");
        if (imageCats.size() != 1)return;
        String url ="";
        for (Neko image : imageCats) {
            url =   image.get("url");
        }
        if(Objects.equals(url, ""))return;
        String tst = OcrUtils.ocrImg(OcrUtils.getFileStream(url));
        System.out.println(tst);
        if (!tst.contains("四川天府健康通")
                && !tst.contains("扫场所码")
                && !tst.contains("个人伟息")
                && !tst.contains("个人侈息")
                && !tst.contains("健康码"))
            return ;

        String a = InTimer.getDate("yyyy-MM-dd");
        String cat = "[CAT:at,code="+groupMsg.getAccountInfo().getAccountCode()+"]";
        String jkmrem = H2Util.Gettableitem(groupMsg.getGroupInfo().getGroupCode()+"jkm", NumberUtil.parseLong(groupMsg.getAccountInfo().getAccountCode()));
        if(jkmrem!=null&&jkmrem.equals(a)){
            sender.SENDER.sendGroupMsg(groupMsg,cat+"你今天已经提交成功健康码了！"+bug);
            return;
        }
        String[] TImes2 = a.split("-");
        if (!tst.contains(a)
                &&!tst.contains("-"+TImes2[2]+"\"},")
                &&!tst.contains("-"+TImes2[2]+"\",")
        ){
            sender.SENDER.sendGroupMsg(groupMsg,cat+"你不能提交不是今天的健康码!"+bug);
            return;
        }

        String neam = groupMsg.getAccountInfo().getAccountRemark();
        System.out.println(neam);
        if (neam==null){
            neam = groupMsg.getAccountInfo().getAccountCode();
        }else {
            neam= neam.replace("/"," ")
                    .replace("\\"," ")
                    .replace(":"," ")
                    .replace("*"," ")
                    .replace("\""," ")
                    .replace("<"," ")
                    .replace(">"," ")
                    .replace("|"," ")
                    .replace("?"," ");
        }

        File muluss = new File("健康码存储仓");
        File mulo =  new File(muluss.getAbsolutePath() +"/"+groupMsg.getGroupInfo().getGroupCode()+"/"+a);
        File mulu = new File(muluss.getAbsolutePath() +"/"+groupMsg.getGroupInfo().getGroupCode());
        if(!muluss.exists()) { muluss.mkdir(); }
        if(!mulu.exists()) { mulu.mkdir(); }
        if(!mulo.exists()) { mulo.mkdir(); }
        URL urls =  new URL(url);
        DataInputStream dis = new DataInputStream(urls.openStream());
        String newImageName=mulo.getAbsolutePath()+"/"+neam+".png";
        FileOutputStream fos = new FileOutputStream(newImageName);
        byte[] buffer = new byte[1024];
        int length;
        while((length = dis.read(buffer))>0){
            fos.write(buffer,0,length);
        }
        dis.close();
        fos.close();
        H2Util.Settableitem(groupMsg.getGroupInfo().getGroupCode()+"jkm", NumberUtil.parseLong(groupMsg.getAccountInfo().getAccountCode()), InTimer.getDate("yyyy-MM-dd"));
        sender.SENDER.sendGroupMsg(groupMsg,cat+"\r\n提交成功！\r\n提交时间: "+InTimer.getDate("yyyy-MM-dd HH:mm:ss:SSS")+bug);

    }



}

