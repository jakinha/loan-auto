package cn.web.util;

import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.testng.Assert;

public class ReadExcel {
	
	static Logger logger = Logger.getLogger(ReadExcel.class);
	
	/**
	 *	获取用例的开始行和结束行
	 * @param id	@test 方法 传过来的id参数
	 * @param sheetname
	 * @return	返回一个数组，第一个元素是开始行，第二个元素是结束行
	 */
	public static int[] getStepAndEndStep(String caseId,Sheet sheet){
		
		int startRow=0;//定义用例执行的开始行变量,开始行数从下标0数
		int endRow=0;//定义用例执行的结束行变量
		int stepAndEndStep[] = new int[2];
		try{
			for(int rowNum=0;rowNum<=sheet.getLastRowNum();rowNum++){//外层循环找到该用例开始行
				//声明Row对象
				Row row=sheet.getRow(rowNum);
				String numId=row.getCell(0).getStringCellValue();
				if(caseId.equals(numId)){
					startRow=rowNum;
					endRow=startRow;
					int k=startRow;
					for(; k<=sheet.getLastRowNum(); k++){//内层循环找到该用例结束行
						if(sheet.getRow(k).getCell(0).getStringCellValue().equals(numId))
							endRow++;
						else
							break;
					}
					break;
				}
			}
			stepAndEndStep[0]=startRow;
			stepAndEndStep[1]=endRow;
			return stepAndEndStep;
		}catch(Exception e){
			e.printStackTrace();
			Assert.fail("读取excel文件行数出现异常,请检查文件更新并保存");
		}
		return null;
	}
}
