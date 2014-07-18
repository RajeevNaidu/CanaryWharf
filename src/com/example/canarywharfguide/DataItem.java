package com.example.canarywharfguide;

public class DataItem
{
   String title,item,price;
   int imageID;
   DataItem(String title)
   {
	   this(null,null,0);
	   this.title=title;
	   
   }
   DataItem(String item,String price,int image)
   {
	   this.item=item;
	   this.price=price;
	   this.imageID=image;
   }
   public void setTitle(String title)
   {
	   
	   this.title=title;
   }
   public String getTilte()
   {
	   return this.title;
   }
   public String getItem()
   {
	   return this.item;
   }
   public void setItem(String item)
   {
	   this.item=item;
   }
   public void setPrice(String price)
   {
	   this.price=price;
   }
   public String getPrice()
   {
	   return this.price;
   }
   public void setIcon(int icon)
   {
	   this.imageID=icon;
   }
   public int getIcon()
   {
	   return this.imageID;
   }
}
