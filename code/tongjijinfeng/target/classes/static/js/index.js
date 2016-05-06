(function(){

$(function(){
      //课程信息
      var infos = [
            {
                  "avatar": "images/Audrey.png", //头像
                  "name": "Audrey", //名字
                  "classname": "开心拿Offer之面试技巧", //课程名称
                  "site": "广东财经大学", //讲课地址
                  "date": "2015年10月13日", //讲课时间
                  "teacher": "从事企业培训逾15年。企业大学课程设计师，认证培训讲师。曾于安利、AIA等世界五百强公司任职。",// 导师简介
                  "classdetail": "如果想要拿到满意的Offer，一定需要了解企业需要找什么样的人，以及面试中会用到的STAR技巧。让你更清楚的找准自己的定位！"//课程简介
            },
            {
                  "avatar": "images/none.jpg", //头像
                  "name": "Katherine", //名字
                  "classname": "开心拿Offer之模拟面试", //课程名称
                  "site": "广东财经大学", //讲课地址
                  "date": "2015年10月20日", //讲课时间
                  "teacher": "宝洁95级管理培训生，宝洁工作经验逾10年，曾负责宝洁全国KA渠道的沃尔玛、和记黄埔南区销售总监等职。",// 导师简介
                  "classdetail": "开心拿Offer之模拟面试-500强企业管理人员为同学们进行的模拟面试，让你身临其境，全面揭开世界500强的神秘面纱！"//课程简介
            },
            {
                  "avatar": "images/Chester.png", //头像
                  "name": "Chester", //名字
                  "classname": "开心拿Offer之模拟面试", //课程名称
                  "site": "广东财经大学", //讲课地址
                  "date": "2015年10月27日", //讲课时间
                  "teacher": "从事人力资源行业逾10年，曾任职于世界500强企业可口可乐等知名企业，讲课幽默风趣。",// 导师简介
                  "classdetail": "模拟世界500强的真实面试场境，手把手教你如何做面试前的准备，如何回答面试官的问题，让你与世界500强接触零距离！"//课程简介
            },
            {
                  "avatar": "images/Bonnie.png", //头像
                  "name": "Bonnie", //名字
                  "classname": "开心拿Offer之如何撰写简历", //课程名称
                  "site": "暨南大学", //讲课地址
                  "date": "2015年11月7日", //讲课时间
                  "teacher": "逾10年人力资源工作经验，其中包括多年跨国猎头公司首席顾问，服务过IBM,Microsoft等知名企业。目前担任EF英孚教育招聘及员工发展部区域经理。",// 导师简介
                  "classdetail": "现场教你如何写出一份满分的简历；如何精准而完美的呈现自己；如何迅速抓住HR的眼球，让自己在数以万计的简历中脱颖而出！"//课程简介
            },
            {
                  "avatar": "images/Sophie.png", //头像
                  "name": "Sophie", //名字
                  "classname": "职业生涯规划-如何成为优秀的世界500强成员", //课程名称
                  "site": "暨南大学", //讲课地址
                  "date": "2015年11月7日", //讲课时间
                  "teacher": "拥有15年人力资源工作经验，历任百事人力资源经理、培训经理，曾负责分公司、华南区、华西区招聘组建、薪酬、培训发展、考核等工作。",// 导师简介
                  "classdetail": "用丰富的案例告诉你，如何成为优秀的世界500强成员，500强企业喜欢什么人，如何做好自己的职业规划。"//课程简介
            },
            {
                  "avatar": "images/Lucia.png", //头像
                  "name": "Lucia", //名字
                  "classname": "开心拿Offer之MBTI", //课程名称
                  "site": "华南理工大学", //讲课地址
                  "date": "2015年12月初", //讲课时间
                  "teacher": "近20年工作经验，曾在多家世界500强企业任职，包括安利、BP及雅芳。",// 导师简介
                  "classdetail": "为何需要自我认知，如何才能真正的了解自己，了解自己后要如何把握好自己在择业、职场、个人发展上的选择，以及自我认知的工具简介（MBTI的理论基础以及在择业、个人发展方面的应用）"//课程简介
            }
      ];
      //联系方式 ：电话、邮箱、微信
      var links = ["+86-20-8380-6630", "lead@aleadin.com", "暂未开通","Aleadin1","广州市艾英领信息科技有限公司 粤ICP备15104204号" ];
      var sidenames = ["secondPage", "3rdPage", "4thpage", "5thpage", "6thpage", "7thpage", "8thpage" ,"9thpage" ,"lastPage"];
      var html = "";
      var index = infos.length % 2;
      console.log(index);
      for(var i = 0; i <  infos.length; i ++){
            html += '<div class="section part2">'
                  + '<div class="container">'
                  + '<div class="clearfix">'
                  + '<div class="fl">'
                  + '<img src="' + infos[i].avatar + '" class="avatar">'
                  + '<p class="ta">'+ infos[i].name + '</p>'
                  + '</div>'
                  + '<div class="fr info">'
                  + '<span class="tip"></span>'
                  + '<p class="info-p">'
                  + '<label class="icon icon1">课程名称</label>'
                  + '<label class="info-word">' + infos[i].classname + '</label>'
                  + '</p><p class="info-p">'
                  + '<label class="icon icon2">讲课地址</label>'
                  + '<label class="info-word">' + infos[i].site + '</label>'
                  + '</p><p class="info-p">'
                  + '<label class="icon icon1">讲课时间</label>'
                  + '<label class="info-word">' + infos[i].date + '</label>'
                  + '</p><p class="info-p">'
                  + '<label class="icon icon1">导师简介</label>'
                  + '<label class="info-word">' + infos[i].teacher + '</label>'
                  + '</p><p class="info-p">'
                  + '<label class="icon icon1">课程简介</label>'
                  + '<label class="info-word">' + infos[i].classdetail + '</label>'
                  + '</p></div></div>';
            ++ i;
            if(i < infos.length){
                  html += '<div class="clearfix mt30">'
                        + '<div class="fl info">'
                        + '<span class="tip"></span>'
                        + '<p class="info-p">'
                        + '<label class="icon icon1">课程名称</label>'
                        + '<label class="info-word">' + infos[i].classname + '</label>'
                        + '</p><p class="info-p">'
                        + '<label class="icon icon2">讲课地址</label>'
                        + '<label class="info-word">' + infos[i].site + '</label>'
                        + '</p><p class="info-p">'
                        + '<label class="icon icon1">讲课时间</label>'
                        + '<label class="info-word">' + infos[i].date + '</label>'
                        + '</p><p class="info-p">'
                        + '<label class="icon icon1">导师简介</label>'
                        + '<label class="info-word">' + infos[i].teacher + '</label>'
                        + '</p><p class="info-p">'
                        + '<label class="icon icon1">课程简介</label>'
                        + '<label class="info-word">' + infos[i].classdetail + '</label>'
                        + '</p></div>'
                        + '<div class="fr">'
                        + '<img src="' + infos[i].avatar + '" class="avatar">'
                        + '<p class="ta">'+ infos[i].name + '</p>'
                        + '</div>'
                        + '</div></div></div>';
            }
            else{
                  console.log(index == 0);
                  if(index == 0){
                        
                        html += '</div><div class="section part2">'
                              + '<div class="container">';
                  }
                  html += '<div class="clearfix ta mt30">'
                        + '<div class="side-part fl">'
                        + '<div class="link">'
                        + '<img src="images/phone.jpg" class="con-icon fl">'
                        + '<p class="title">联系电话：</p>'
                        + '<p class="detail">' + links[0] +'</p>'
                        + '</div><div class="link">'
                        + '<img src="images/email.jpg" class="con-icon fl">'
                        + '<p class="title">客服邮箱：</p>'
                        + '<p class="detail">' + links[1] +'</p>'
                        + '</div></div><img src="images/contect.jpg" class="link-img fl">'
                        + '<div class="side-part fr">'
                        + '<div class="link">'
                        + '<img src="images/qq.jpg" class="con-icon fl">'
                        + '<p class="title">QQ群：</p>'
                        + '<p class="detail">' + links[2] +'</p>'
                        + '</div><div class="link">'
                        + '<img src="images/weixin.jpg" class="con-icon fl">'
                        + '<p class="title">微信公众号：</p><p class="detail">' + links[3] +'</p>'
                        + '</div></div></div><div class="clearfix ta mt30">'
                        + '<p>'+links[4]+'</p>'
                        + '</div>';
            }
      }

      if(index == 0){
             html += '</div><div class="section part2">'
                  + '<div class="container">'
                  + '<div class="clearfix ta mt160">'
                  + '<div class="side-part fl">'
                  + '<div class="link">'
                  + '<img src="images/phone.jpg" class="con-icon fl">'
                  + '<p class="title">联系电话：</p>'
                  + '<p class="detail">' + links[0] +'</p>'
                  + '</div><div class="link">'
                  + '<img src="images/email.jpg" class="con-icon fl">'
                  + '<p class="title">客服邮箱：</p>'
                  + '<p class="detail">' + links[1] +'</p>'
                  + '</div></div><img src="images/contect.jpg" class="link-img fl">'
                  + '<div class="side-part fr">'
                  + '<div class="link">'
                  + '<img src="images/qq.jpg" class="con-icon fl">'
                  + '<p class="title">QQ群：</p>'
                  + '<p class="detail">' + links[2] +'</p>'
                  + '</div><div class="link">'
                  + '<img src="images/weixin.jpg" class="con-icon fl">'
                  + '<p class="title">微信公众号：</p><p class="detail">' + links[3] +'</p>'
                  + '</div></div></div><div class="clearfix ta mt30">'
                  + '<p>'+links[4]+'</p>'
                  + '</div>';
      }
      $("#fullpage").append(html);
      var length = infos.length + 1;
      
      var text = "";
      for(var i = 0; i <  length /2 + index ; ++ i){
            text += '<a class="list-btn" data-menuanchor="' + sidenames[i] + '" href="#' + sidenames[i] + '"></a>';
      }
      $("#menutool").append(text);
	$('#fullpage').fullpage({
		verticalCentered: true,
		resize: true,
		anchors: ['firstPage', 'secondPage', '3rdPage', '4thpage', '5thpage','lastPage'],
		// navigation: true,
		// navigationColor: "#ffff00",
		menu: '#menutool',
		scrollingSpeed: 1700,
		onLeave :function(index, nextIndex, direction){
           
            if(nextIndex == 1){
                  $(".side").fadeOut();
            }
            else{
                  $(".side").fadeIn(1500);
            }
        }
	});

});
})();