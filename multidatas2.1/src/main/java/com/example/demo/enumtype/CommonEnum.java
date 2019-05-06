package com.example.demo.enumtype;

/**
 * 
 * @author wangyong
 *
 */
public class CommonEnum {
	
	public static void main(String[] args) {
		InUseEnum valueOf = InUseEnum.valueOf("是");
		System.out.println(valueOf.getIndex());
	}
	/**
	 * 是否启用枚举(0否、1是)
	 */
	public enum InUseEnum {
		是("是", 1), 否("否", 0);
		private String name;
		private int index;

		/**
		 * 构造函数
		 *
		 * @param name
		 *            名称
		 * @param index
		 *            枚举值
		 */
		InUseEnum(String name, int index) {
			this.name = name;
			this.index = index;
		}

		public static String getName(int index) {
			for (InUseEnum c : InUseEnum.values()) {
				if (c.getIndex() == index) {
					return c.name;
				}
			}
			return null;
		}

		public static InUseEnum getEnum(int index) {
			for (InUseEnum c : InUseEnum.values()) {
				if (c.getIndex() == index) {
					return c;
				}
			}
			return null;
		}

		public String getName() {
			return name;
		}

		public int getIndex() {
			return index;
		}

	}
	/**
	 * 是否男女
	 */
	public enum SexEnum {
		男("男", 1), 女("女", 0);
		private String name;
		private int index;

		/**
		 * 构造函数
		 *
		 * @param name
		 *            名称
		 * @param index
		 *            枚举值
		 */
		SexEnum(String name, int index) {
			this.name = name;
			this.index = index;
		}

		public static String getName(int index) {
			for (InUseEnum c : InUseEnum.values()) {
				if (c.getIndex() == index) {
					return c.name;
				}
			}
			return null;
		}

		public static SexEnum getEnum(int index) {
			for (SexEnum c : SexEnum.values()) {
				if (c.getIndex() == index) {
					return c;
				}
			}
			return null;
		}

		public String getName() {
			return name;
		}

		public int getIndex() {
			return index;
		}

	}
	


	
	/**
	 * Excel文件类型(0未知、1Excel2003、2Excel2007)
	 */
	public enum ExcelFileTypeEnum {
		ExcelUnKnown("ExcelUnKnown", 0), Excel97_2003("Excel97-2003(HSSF)", 1), Excel2007Plus("Excel2007+(XSSF)", 2);
		private String name;

		public void setName(String name) {
			this.name = name;
		}

		public void setIndex(int index) {
			this.index = index;
		}

		private int index;

		public String getName() {
			return name;
		}

		public int getIndex() {
			return index;
		}

		ExcelFileTypeEnum(String name, int index) {
			this.name = name;
			this.index = index;
		}
	}
}
