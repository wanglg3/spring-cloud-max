package cloud.max.file.config;

import cloud.max.file.properties.FileProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.lang.NonNull;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.io.File;
import java.util.List;

/**
 * {@link WebMvcConfigurationSupport} 优先级比 {@link WebMvcConfigurer} 高
 * <p>
 * 使用了 {@link WebMvcConfigurationSupport} 之后，{@link WebMvcConfigurer} 会失效
 *
 *
 * 1. classpath: 前缀
 * 含义：classpath: 表示从类路径中加载资源。类路径是指Java应用程序的类和资源文件的存放位置，通常包括项目的src/main/resources目录和依赖的jar包。
 * 用途：使用 classpath:/static/ 可以方便地访问项目中的静态资源，比如图片、CSS、JavaScript等。这些资源通常在打包时会被包含在jar包或war包中。
 * 示例：如果你在项目的src/main/resources/static/目录下有一个文件image.png，可以通过classpath:/static/image.png来访问它。
 * 2. file: 前缀
 * 含义：file: 表示从文件系统中加载资源。它后面跟随的是一个绝对路径或相对路径，指向本地文件系统中的某个位置。
 * 用途：使用 file: 可以直接访问文件系统中的文件，适合需要动态加载文件或在开发环境中使用本地文件的场景。
 * 示例：如果你有一个文件在D:/upload/image.png，可以通过file:D:/upload/image.png来访问它。
 * 总结
 * classpath: 适用于在应用程序打包后需要访问的资源，通常用于静态文件。
 * file: 适用于直接访问本地文件系统中的文件，适合动态加载或开发时使用。
 *
 */
@Slf4j
@Configuration
public class WebMvcConfigurationSupportConfig extends WebMvcConfigurationSupport {

	private FileProperties fileProperties;

	@Autowired
	public void setFileProperties(FileProperties fileProperties) {
		this.fileProperties = fileProperties;
	}

	@Override
	protected void addResourceHandlers(@NonNull ResourceHandlerRegistry registry) {

		registry.addResourceHandler("/**").addResourceLocations("classpath:/static/");

		List<FileProperties.ResourceHandler> resourceHandlers = fileProperties.getResourceHandlers();

		if (resourceHandlers == null || resourceHandlers.isEmpty()) {
			log.warn("本地静态资源配置为空");
		}
		else {
			for (FileProperties.ResourceHandler resourceHandler : resourceHandlers) {
				String addResourceHandler = resourceHandler.getAddResourceHandler();
				String addResourceLocations = resourceHandler.getAddResourceLocations();

				log.info("网路路径: {} 映射到本地路径: {}", addResourceHandler, addResourceLocations);

				File directory = new File(addResourceLocations.replaceFirst("file:", ""));
				if (directory.exists()) {
					log.info("文件夹: {} 已存在", addResourceLocations);
				}
				else {
					boolean mkdirs = directory.mkdirs();
					log.info("文件夹: {} 创建结果: {}", addResourceLocations, mkdirs);
				}

				registry.addResourceHandler(addResourceHandler).addResourceLocations(addResourceLocations);
			}

		}

	}

}
