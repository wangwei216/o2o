package com.imooc.o2o.util;


/*
*设定保存路径
设定缓冲路径
设定缓冲大小
设定文件类型
获取文件扩展名
验证文件类型有效性
表单内容获取：文字以键值对保存在map中。文件保存到保存目录下
获取上传的文件内容们（返回map给调用者）
*
*
*
* */

import com.imooc.o2o.dto.ImageHolder;
import net.coobird.thumbnailator.Thumbnails;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ImageUtil {

	//把这个基本的哪个盘的路径给定义下来 这个可以先不用
//	private static String basePath=Thread.currentThread().getContextClassLoader().getResource("").getPath();

	//生成缩略图
	public static String generateThumbnail(ImageHolder thumbnail, String targetAddr) {
		//生成随机图片的文件名，
		String realFileName = FileUtil.getRandomFileName();
		//获得上传文件流的扩展名
//这里有问题
		String extension = getFileExtension(thumbnail.getImageName());
		makeDirPath(targetAddr);
		//返回绝对路径的拼接的组合名
		String relativeAddr = targetAddr + realFileName + extension;
		File dest = new File(FileUtil.getImgBasePath() + relativeAddr);
		try {
			Thumbnails.of(thumbnail.getImage()).size(200, 200).outputQuality(0.25f).toFile(dest);
		} catch (IOException e) {
			throw new RuntimeException("创建缩略图失败：" + e.toString());
		}
		return relativeAddr;
	}

	public static String generateNormalImg(ImageHolder thumbnail, String targetAddr) {
		//这个是生成图片的随机名
		String realFileName = FileUtil.getRandomFileName();
		String extension = getFileExtension(thumbnail.getImageName());
		makeDirPath(targetAddr);
		String relativeAddr = targetAddr + realFileName + extension;
		File dest = new File(FileUtil.getImgBasePath() + relativeAddr);
		try {
			Thumbnails.of(thumbnail.getImage()).size(337, 640).outputQuality(0.5f).toFile(dest);
		} catch (IOException e) {
			throw new RuntimeException("创建缩略图失败：" + e.toString());
		}
		return relativeAddr;
	}

	public static List<String> generateNormalImgs(List<CommonsMultipartFile> imgs, String targetAddr) {
		int count = 0;
		List<String> relativeAddrList = new ArrayList<String>();
		if (imgs != null && imgs.size() > 0) {
			makeDirPath(targetAddr);
			for (CommonsMultipartFile img : imgs) {
				String realFileName = FileUtil.getRandomFileName();
				String extension = getFileExtension(img.getName());
				String relativeAddr = targetAddr + realFileName + count + extension;
				File dest = new File(FileUtil.getImgBasePath() + relativeAddr);
				count++;
				try {
					Thumbnails.of(img.getInputStream()).size(600, 300).outputQuality(0.5f).toFile(dest);
				} catch (IOException e) {
					throw new RuntimeException("创建图片失败：" + e.toString());
				}
				relativeAddrList.add(relativeAddr);
			}
		}
		return relativeAddrList;
	}

	//创建一个目标路径文件夹
	private static void makeDirPath(String targetAddr) {
		String realFileParentPath = FileUtil.getImgBasePath() + targetAddr;
		File dirPath = new File(realFileParentPath);
		if (!dirPath.exists()) {
			dirPath.mkdirs();
		}
	}

	/*这个是得到输入上传文件流的扩展名*/
	private static String getFileExtension(String cFile) {


		String prefix=cFile.substring(cFile.lastIndexOf(".")+1);
		return prefix;

		/*String originalFileName = cFile.getOriginalFilename();
		return originalFileName.substring(originalFileName.lastIndexOf("."));*/
	}

	/*这个是删除文件*/
	public static void deleteFileOrPath(String storePath){
		File fileOrPath ;
	}
}
