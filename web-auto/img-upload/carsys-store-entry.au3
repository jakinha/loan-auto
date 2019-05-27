If $CmdLine[0]<3 Then Exit EndIf

;$CmdLine[1]
;$CmdLine[2]
;$CmdLine[3]

handleUpload($CmdLine[2],$CmdLine[3])

Func handleUpload($uploadfile,$uploadfile2)

	If WinWait("华夏信财影像系统","",8) Then ;等待弹出出现，最大等待时间是8秒
	
		WinActivate("华夏信财影像系统");激活当前华夏信财影像系统窗口
		
		If $CmdLine[1] == "门店录入" Then
		
			;申请表
			ControlClick("华夏信财影像系统","","WindowsForms10.STATIC.app.0.19c1610_r13_ad151")
			sameOprea($uploadfile)
			
			;身份证
			ControlClick("华夏信财影像系统","","WindowsForms10.STATIC.app.0.19c1610_r13_ad151")
			sameOprea($uploadfile)

			;行驶证
			ControlClick("华夏信财影像系统","","WindowsForms10.STATIC.app.0.19c1610_r13_ad150")
			sameOprea($uploadfile)
			
			;驾驶证
			ControlClick("华夏信财影像系统","","WindowsForms10.STATIC.app.0.19c1610_r13_ad149")
			sameOprea($uploadfile)
			
			;车辆登记证
			ControlClick("华夏信财影像系统","","WindowsForms10.STATIC.app.0.19c1610_r13_ad148")
			sameOprea($uploadfile)
			
			;保险单
			ControlClick("华夏信财影像系统","","WindowsForms10.STATIC.app.0.19c1610_r13_ad147")
			sameOprea($uploadfile)
			
			startAllUpload()
			Sleep(2500)
		EndIf	
			
		If 	$CmdLine[1] == "本地评估" Then
			;父级-车辆照片
			ControlClick("华夏信财影像系统","","WindowsForms10.STATIC.app.0.19c1610_r13_ad151")
			Sleep(1000)
			
			;子级-车辆照片
			ControlClick("华夏信财影像系统","","WindowsForms10.STATIC.app.0.19c1610_r13_ad144")
			sameOprea($uploadfile)
			
			
			;车辆违规截图
			ControlClick("华夏信财影像系统","","WindowsForms10.STATIC.app.0.19c1610_r13_ad144")
			sameOprea($uploadfile)
			
			;车辆现场复核单或评估报告
			ControlClick("华夏信财影像系统","","WindowsForms10.STATIC.app.0.19c1610_r13_ad143")
			sameOprea($uploadfile)

			ControlClick("华夏信财影像系统","","WindowsForms10.STATIC.app.0.19c1610_r13_ad145")
			
			
			$a="WindowsForms10.Window.8.app.0.19c1610_r13_ad1137"
			$b="WindowsForms10.Window.8.app.0.19c1610_r13_ad1139"
			$c="WindowsForms10.Window.8.app.0.19c1610_r13_ad1141"
			$d="WindowsForms10.Window.8.app.0.19c1610_r13_ad1143"
			$e="WindowsForms10.Window.8.app.0.19c1610_r13_ad1145"
			$f="WindowsForms10.Window.8.app.0.19c1610_r13_ad1147"
			$g="WindowsForms10.Window.8.app.0.19c1610_r13_ad1149"
			$h="WindowsForms10.Window.8.app.0.19c1610_r13_ad1151"
			$i="WindowsForms10.Window.8.app.0.19c1610_r13_ad1153"
			$j="WindowsForms10.Window.8.app.0.19c1610_r13_ad1155"
			
			$aa="WindowsForms10.Window.8.app.0.19c1610_r13_ad1157"
			$bb="WindowsForms10.Window.8.app.0.19c1610_r13_ad1159"
			$cc="WindowsForms10.Window.8.app.0.19c1610_r13_ad1161"
			$dd="WindowsForms10.Window.8.app.0.19c1610_r13_ad1163"
			$ee="WindowsForms10.Window.8.app.0.19c1610_r13_ad1165"
			$ff="WindowsForms10.Window.8.app.0.19c1610_r13_ad1167"
			$gg="WindowsForms10.Window.8.app.0.19c1610_r13_ad1169"
			$hh="WindowsForms10.Window.8.app.0.19c1610_r13_ad1171"
			$ii="WindowsForms10.Window.8.app.0.19c1610_r13_ad1173"

			uploadMoreImg($uploadfile,$a)
			uploadMoreImg($uploadfile,$b)
			uploadMoreImg($uploadfile,$c)
			uploadMoreImg($uploadfile,$d)
			uploadMoreImg($uploadfile,$e)
			uploadMoreImg($uploadfile,$f)
			uploadMoreImg($uploadfile,$g)
			uploadMoreImg($uploadfile,$h)
			uploadMoreImg($uploadfile,$i)
			uploadMoreImg($uploadfile,$j)
			
			uploadMoreImg($uploadfile,$aa)
			uploadMoreImg($uploadfile,$bb)
			uploadMoreImg($uploadfile,$cc)
			uploadMoreImg($uploadfile,$dd)
			uploadMoreImg($uploadfile,$ee)
			uploadMoreImg($uploadfile,$ff)
			uploadMoreImg($uploadfile,$gg)
			uploadMoreImg($uploadfile,$hh)
			uploadMoreImg($uploadfile,$ii)
			
			Sleep(1000)
			ControlClick("华夏信财影像系统","","WindowsForms10.Window.8.app.0.19c1610_r13_ad1191") ;上传按钮
			
			WinWait("影像上传/更新","",3)
			WinActivate("影像上传/更新");激活当前影像上传/更新窗口
			ControlClick("影像上传/更新","","WindowsForms10.Window.8.app.0.19c1610_r13_ad15") ;全部开始 按钮
			
			Sleep(4000)
		
		EndIf
		
		If	$CmdLine[1] == "gps安装" Then
		
		
		EndIf
		
		If 	$CmdLine[1] == "门店签约" Then
		
		
		EndIf
		
		
		ControlClick("影像上传/更新","","WindowsForms10.Window.8.app.0.19c1610_r13_ad13") ;关闭 上传成功的窗口

		WinWait("华夏信财影像系统","",3)
		WinActivate("华夏信财影像系统");激活当前华夏信财影像系统窗口
		ControlClick("华夏信财影像系统","","WindowsForms10.Window.8.app.0.19c1610_r13_ad1130") ;关闭影像系统窗口
		Sleep(1000)
		ControlClick("退出提示","","Button1") ;确定
		
	Else
		return False	
	EndIf

EndFunc

;导入文件
Func sameOprea($uploadfile)
			
			Sleep(1000)
			ControlClick("华夏信财影像系统","","WindowsForms10.Window.8.app.0.19c1610_r13_ad1135");单击导入
			
			WinWait("打开","",4);等待弹出出现，最大等待时间是4秒
			WinActivate("打开");找到弹出窗口之后，激活当前窗口

			ControlSetText("打开","","Edit1",$uploadfile)   ;把文件路径放入输入框，此”Edit1“是用FinderTool获取到的
			ControlClick("打开","","Button1")
			WinActivate("华夏信财影像系统")
EndFunc

Func uploadMoreImg($uploadfile,$goalbut)
			
			Sleep(1000)
			ControlClick("华夏信财影像系统","",$goalbut);单击导入
			
			WinWait("打开","",4);等待弹出出现，最大等待时间是4秒
			WinActivate("打开");找到弹出窗口之后，激活当前窗口

			ControlSetText("打开","","Edit1",$uploadfile)   ;把文件路径放入输入框，此”Edit1“是用FinderTool获取到的
			ControlClick("打开","","Button1")
			WinActivate("华夏信财影像系统")
EndFunc

;上传全部文件并关闭影像系统
Func startAllUpload()
		ControlClick("华夏信财影像系统","","WindowsForms10.Window.8.app.0.19c1610_r13_ad1133") ;上传按钮
		WinWait("影像上传/更新","",3)
		WinActivate("影像上传/更新");激活当前影像上传/更新窗口
		ControlClick("影像上传/更新","","WindowsForms10.Window.8.app.0.19c1610_r13_ad15") ;全部开始 按钮

EndFunc