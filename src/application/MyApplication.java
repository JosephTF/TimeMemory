package application;
/**
 * @author 王越
 *  配置Universal-ImageLoader，加载网络图片和本地图片
 *  记得配置AndroidManifest里application的name
 */

import java.io.File;

import android.app.Application;
import android.graphics.Bitmap;
import android.os.Environment;
import android.util.Log;

import com.joseph.timememory.R;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiscCache;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.UsingFreqLimitedMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;

public class MyApplication extends Application {
	private String mFilePath;
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		mFilePath = getImageDir();
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
				this)
				.memoryCacheExtraOptions(480, 800)
				// max width, max height，即保存的每个缓存文件的最大长宽
				.discCacheExtraOptions(480, 800, null)
				// Can slow ImageLoader, use it carefully (Better don't use
				// it)/设置缓存的详细信息，最好不要设置这个
				.threadPoolSize(3)
				// 线程池内加载的数量
				.threadPriority(Thread.NORM_PRIORITY - 2)
				.denyCacheImageMultipleSizesInMemory()
				.memoryCache(new UsingFreqLimitedMemoryCache(2 * 1024 * 1024))
				// You can pass your own memory cache
				// implementation/你可以通过自己的内存缓存实现
				.memoryCacheSize(2 * 1024 * 1024)
				.discCacheSize(50 * 1024 * 1024)
				.discCacheFileNameGenerator(new Md5FileNameGenerator())
				// 将保存的时候的URI名称用MD5 加密
				.tasksProcessingOrder(QueueProcessingType.LIFO)
				.discCacheFileCount(100)
				// 缓存的文件数量
				.discCache(
						new UnlimitedDiscCache(new File(mFilePath)))
				// 自定义缓存路径
				.defaultDisplayImageOptions(getDisplayOptions())
				.imageDownloader(
						new BaseImageDownloader(this, 5 * 1000, 30 * 1000))
				.writeDebugLogs() // Remove for release app
				.build();// 开始构建
		ImageLoader.getInstance().init(config);
		
	}

	// 创建Image文件夹
	public String getImageDir() {
		// SD卡指定文件夹
		String sdcardPath = Environment.getExternalStorageDirectory()
				.toString();
		// 指定文件夹创建路径
		File dir = new File(sdcardPath + File.separator + "TimeMemory"
				+ File.separator + "imgCache");
		if (dir.exists()) {
			return dir.toString();
		} else {
			dir.mkdirs();
			Log.d("BAG", "保存路径不存在,");
			return dir.toString();
		}
	}

	private DisplayImageOptions getDisplayOptions() {
		DisplayImageOptions options;
		options = new DisplayImageOptions.Builder()
				.showImageForEmptyUri(R.drawable.error404)// 设置图片Uri为空或是错误的时候显示的图片
				.showImageOnFail(R.drawable.errornet) // 设置图片加载/解码过程中错误时候显示的图片
				.cacheInMemory(true)// 设置下载的图片是否缓存在内存中
				.cacheOnDisc(true)// 设置下载的图片是否缓存在SD卡中
				.considerExifParams(true) // 是否考虑JPEG图像EXIF参数（旋转，翻转）
				.imageScaleType(ImageScaleType.EXACTLY_STRETCHED)// 设置图片以如何的编码方式显示
				.bitmapConfig(Bitmap.Config.RGB_565)// 设置图片的解码类型//
				.resetViewBeforeLoading(true)// 设置图片在下载前是否重置，复位
				.displayer(new RoundedBitmapDisplayer(20))// 是否设置为圆角，弧度为多少
				.displayer(new FadeInBitmapDisplayer(100))// 是否图片加载好后渐入的动画时间
				.build();// 构建完成
		return options;
	}
}
