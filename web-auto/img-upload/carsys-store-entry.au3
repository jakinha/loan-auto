If $CmdLine[0]<3 Then Exit EndIf

;$CmdLine[1]
;$CmdLine[2]
;$CmdLine[3]

handleUpload($CmdLine[2],$CmdLine[3])

Func handleUpload($uploadfile,$uploadfile2)

	If WinWait("�����Ų�Ӱ��ϵͳ","",8) Then ;�ȴ��������֣����ȴ�ʱ����8��
	
		WinActivate("�����Ų�Ӱ��ϵͳ");���ǰ�����Ų�Ӱ��ϵͳ����
		
		If $CmdLine[1] == "�ŵ�¼��" Then
		
			;�����
			ControlClick("�����Ų�Ӱ��ϵͳ","","WindowsForms10.STATIC.app.0.19c1610_r13_ad151")
			sameOprea($uploadfile)
			
			;���֤
			ControlClick("�����Ų�Ӱ��ϵͳ","","WindowsForms10.STATIC.app.0.19c1610_r13_ad151")
			sameOprea($uploadfile)

			;��ʻ֤
			ControlClick("�����Ų�Ӱ��ϵͳ","","WindowsForms10.STATIC.app.0.19c1610_r13_ad150")
			sameOprea($uploadfile)
			
			;��ʻ֤
			ControlClick("�����Ų�Ӱ��ϵͳ","","WindowsForms10.STATIC.app.0.19c1610_r13_ad149")
			sameOprea($uploadfile)
			
			;�����Ǽ�֤
			ControlClick("�����Ų�Ӱ��ϵͳ","","WindowsForms10.STATIC.app.0.19c1610_r13_ad148")
			sameOprea($uploadfile)
			
			;���յ�
			ControlClick("�����Ų�Ӱ��ϵͳ","","WindowsForms10.STATIC.app.0.19c1610_r13_ad147")
			sameOprea($uploadfile)
			
			startAllUpload()
			Sleep(2500)
		EndIf	
			
		If 	$CmdLine[1] == "��������" Then
			;����-������Ƭ
			ControlClick("�����Ų�Ӱ��ϵͳ","","WindowsForms10.STATIC.app.0.19c1610_r13_ad151")
			Sleep(1000)
			
			;�Ӽ�-������Ƭ
			ControlClick("�����Ų�Ӱ��ϵͳ","","WindowsForms10.STATIC.app.0.19c1610_r13_ad144")
			sameOprea($uploadfile)
			
			
			;����Υ���ͼ
			ControlClick("�����Ų�Ӱ��ϵͳ","","WindowsForms10.STATIC.app.0.19c1610_r13_ad144")
			sameOprea($uploadfile)
			
			;�����ֳ����˵�����������
			ControlClick("�����Ų�Ӱ��ϵͳ","","WindowsForms10.STATIC.app.0.19c1610_r13_ad143")
			sameOprea($uploadfile)

			ControlClick("�����Ų�Ӱ��ϵͳ","","WindowsForms10.STATIC.app.0.19c1610_r13_ad145")
			
			
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
			ControlClick("�����Ų�Ӱ��ϵͳ","","WindowsForms10.Window.8.app.0.19c1610_r13_ad1191") ;�ϴ���ť
			
			WinWait("Ӱ���ϴ�/����","",3)
			WinActivate("Ӱ���ϴ�/����");���ǰӰ���ϴ�/���´���
			ControlClick("Ӱ���ϴ�/����","","WindowsForms10.Window.8.app.0.19c1610_r13_ad15") ;ȫ����ʼ ��ť
			
			Sleep(4000)
		
		EndIf
		
		If	$CmdLine[1] == "gps��װ" Then
		
		
		EndIf
		
		If 	$CmdLine[1] == "�ŵ�ǩԼ" Then
		
		
		EndIf
		
		
		ControlClick("Ӱ���ϴ�/����","","WindowsForms10.Window.8.app.0.19c1610_r13_ad13") ;�ر� �ϴ��ɹ��Ĵ���

		WinWait("�����Ų�Ӱ��ϵͳ","",3)
		WinActivate("�����Ų�Ӱ��ϵͳ");���ǰ�����Ų�Ӱ��ϵͳ����
		ControlClick("�����Ų�Ӱ��ϵͳ","","WindowsForms10.Window.8.app.0.19c1610_r13_ad1130") ;�ر�Ӱ��ϵͳ����
		Sleep(1000)
		ControlClick("�˳���ʾ","","Button1") ;ȷ��
		
	Else
		return False	
	EndIf

EndFunc

;�����ļ�
Func sameOprea($uploadfile)
			
			Sleep(1000)
			ControlClick("�����Ų�Ӱ��ϵͳ","","WindowsForms10.Window.8.app.0.19c1610_r13_ad1135");��������
			
			WinWait("��","",4);�ȴ��������֣����ȴ�ʱ����4��
			WinActivate("��");�ҵ���������֮�󣬼��ǰ����

			ControlSetText("��","","Edit1",$uploadfile)   ;���ļ�·����������򣬴ˡ�Edit1������FinderTool��ȡ����
			ControlClick("��","","Button1")
			WinActivate("�����Ų�Ӱ��ϵͳ")
EndFunc

Func uploadMoreImg($uploadfile,$goalbut)
			
			Sleep(1000)
			ControlClick("�����Ų�Ӱ��ϵͳ","",$goalbut);��������
			
			WinWait("��","",4);�ȴ��������֣����ȴ�ʱ����4��
			WinActivate("��");�ҵ���������֮�󣬼��ǰ����

			ControlSetText("��","","Edit1",$uploadfile)   ;���ļ�·����������򣬴ˡ�Edit1������FinderTool��ȡ����
			ControlClick("��","","Button1")
			WinActivate("�����Ų�Ӱ��ϵͳ")
EndFunc

;�ϴ�ȫ���ļ����ر�Ӱ��ϵͳ
Func startAllUpload()
		ControlClick("�����Ų�Ӱ��ϵͳ","","WindowsForms10.Window.8.app.0.19c1610_r13_ad1133") ;�ϴ���ť
		WinWait("Ӱ���ϴ�/����","",3)
		WinActivate("Ӱ���ϴ�/����");���ǰӰ���ϴ�/���´���
		ControlClick("Ӱ���ϴ�/����","","WindowsForms10.Window.8.app.0.19c1610_r13_ad15") ;ȫ����ʼ ��ť

EndFunc