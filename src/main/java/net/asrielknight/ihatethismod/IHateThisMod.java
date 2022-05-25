package net.asrielknight.ihatethismod;

import com.google.gson.Gson;
import net.asrielknight.ihatethismod.block.ModBlocks;
import net.asrielknight.ihatethismod.block.entity.ModBlockEntities;
import net.asrielknight.ihatethismod.config.ModConfigs;
import net.asrielknight.ihatethismod.item.ModItems;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.texture.NativeImage;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.*;
import javax.imageio.metadata.IIOMetadata;
import javax.imageio.metadata.IIOMetadataNode;
import javax.imageio.stream.FileImageOutputStream;
import javax.imageio.stream.ImageOutputStream;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

class Config {
	public List<String> pictures = new ArrayList();
}

class GifCreator {
	protected ImageWriter gifWriter;
	protected ImageWriteParam imageWriteParam;
	protected IIOMetadata imageMeta;
	public GifCreator(ImageOutputStream outputStream, int imageType, int timeBetweenFramesMS, boolean loopContinuously) throws IIOException, IOException {
		gifWriter = getWriter();
		imageWriteParam = gifWriter.getDefaultWriteParam();
		ImageTypeSpecifier imageTypeSpecifier = ImageTypeSpecifier.createFromBufferedImageType(imageType);
		imageMeta = gifWriter.getDefaultImageMetadata(imageTypeSpecifier, imageWriteParam);
		String metaFormatName = imageMeta.getNativeMetadataFormatName();
		IIOMetadataNode root = (IIOMetadataNode) imageMeta.getAsTree(metaFormatName);
		IIOMetadataNode graphicsControlExtensionNode = getNode(root, "GraphicControlExtension");
		graphicsControlExtensionNode.setAttribute("disposalMethod", "none");
		graphicsControlExtensionNode.setAttribute("userInputFlag", "FALSE");
		graphicsControlExtensionNode.setAttribute("transparentColorFlag", "FALSE");
		graphicsControlExtensionNode.setAttribute("delayTime", Integer.toString(timeBetweenFramesMS/10));
		graphicsControlExtensionNode.setAttribute("transparentColorIndex", "0");
		IIOMetadataNode commentsNode = getNode(root, "CommentExtensions");
		commentsNode.setAttribute("CommentExtension", "bruh");
		IIOMetadataNode appExtensionsNode = getNode(root, "ApplicationExtensions");
		IIOMetadataNode child = new IIOMetadataNode("ApplicationExtension");
		child.setAttribute("applicationID", "NETSCAPE");
		child.setAttribute("authenticationCode", "2.0");
		int loop = loopContinuously ? 0 : 1;
		child.setUserObject(new byte[]{ 0x1, (byte) (loop & 0xFF), (byte) ((loop >> 8) & 0xFF)});
		appExtensionsNode.appendChild(child);
		imageMeta.setFromTree(metaFormatName, root);
		gifWriter.setOutput(outputStream);
		gifWriter.prepareWriteSequence(null);
	}
	public void writeToSequence(RenderedImage img) throws IOException {
		gifWriter.writeToSequence(new IIOImage(img, null, imageMeta), imageWriteParam);
	}
	public void close() throws IOException {
		gifWriter.endWriteSequence();
	}
	private static ImageWriter getWriter() throws IIOException {
		Iterator<ImageWriter> iter = ImageIO.getImageWritersBySuffix("gif");
		if (!iter.hasNext()) {
			throw new IIOException("No GIF writers exist");
		} else {
			return iter.next();
		}
	}
	private static IIOMetadataNode getNode(IIOMetadataNode rootNode, String nodeName) {
		int nNodes = rootNode.getLength();
		for (int i = 0; i < nNodes; i++) {
			if (rootNode.item(i).getNodeName().compareToIgnoreCase(nodeName) == 0) {
				return ((IIOMetadataNode) rootNode.item(i));
			}
		}
		IIOMetadataNode node = new IIOMetadataNode(nodeName);
		rootNode.appendChild(node);
		return(node);
	}
}

public class IHateThisMod implements ModInitializer {
	public static final String MOD_ID = "ihatethismod";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
	public static ArrayList<InputStream> images = new ArrayList<InputStream>();
	public static ArrayList<NativeImage> nativeImages = new ArrayList<NativeImage>();
	public static String[] imageLinks;
	public static NativeImage nativeImage;
	public static Path path = FabricLoader.getInstance().getConfigDir();

	private static BufferedImage resizeImage(BufferedImage originalImage, Integer img_width, Integer img_height) {
		int type = BufferedImage.TYPE_INT_ARGB;
		BufferedImage resizedImage = new BufferedImage(img_width, img_height, type);
		Graphics2D g = resizedImage.createGraphics();
		g.setComposite(AlphaComposite.Src);
		g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g.drawImage(originalImage, 0, 0, img_width, img_height, null);
		g.dispose();
		return resizedImage;
	}

	public static Dimension getScaledDimension(Dimension imgSize, Dimension boundary) {

		int original_width = imgSize.width;
		int original_height = imgSize.height;
		int bound_width = boundary.width;
		int bound_height = boundary.height;
		int new_width = original_width;
		int new_height = original_height;

		// first check if we need to scale width
		if (original_width > bound_width) {
			//scale width to fit
			new_width = bound_width;
			//scale height to maintain aspect ratio
			new_height = (new_width * original_height) / original_width;
		}

		// then check if we need to scale even with the new height
		if (new_height > bound_height) {
			//scale height to fit instead
			new_height = bound_height;
			//scale width to maintain aspect ratio
			new_width = (new_height * original_width) / original_height;
		}

		return new Dimension(new_width, new_height);
	}

	public static void readConfig() {
		try (FileReader reader = new FileReader(path.resolve("IHTMconf.json").toFile())){
			Gson gson = new Gson();
			Config object = gson.fromJson(reader, Config.class);
			imageLinks = new String[object.pictures.size()];
			object.pictures.toArray(imageLinks);
		} catch (Exception e) {
			LOGGER.info(e.toString());
		}
	}

	public static void getImagesFromImgur() throws IOException, InterruptedException, URISyntaxException {
		for (int i = 0; i < imageLinks.length; i++) {
			String[] albumId = imageLinks[i].split("https://imgur.com/gallery/");
			String newLink = "https://api.imgur.com/3/album/" + albumId[albumId.length - 1] + "/images";
			var client = HttpClient.newHttpClient();
			var request = HttpRequest.newBuilder().uri(new URI(newLink)).GET().header("Authorization", "Client-ID bb9db1128f2e186").build();
			HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
			JSONObject responseJson = new JSONObject(response.body());
			JSONArray responseArray = (JSONArray) responseJson.get("data");
			for (int h = 0, size = responseArray.length(); h < size; h++) {
				JSONObject innerObject = responseArray.getJSONObject(h);
				String link = innerObject.getString("link");
				var requestImg = HttpRequest.newBuilder().uri(new URI(link)).GET().build();
				HttpResponse<InputStream> imgResponse = client.send(requestImg, HttpResponse.BodyHandlers.ofInputStream());
				images.add(imgResponse.body());
			}
		}
		BufferedImage firstImage = ImageIO.read(images.get(0));
		BufferedImage firstImageResized = resizeImage(firstImage, firstImage.getWidth(), firstImage.getWidth());
		ImageOutputStream output = new FileImageOutputStream(new File(String.valueOf(path.resolve("IHTM.gif"))));
		GifCreator writer = new GifCreator(output, firstImage.getType(), 3000, true);
		writer.writeToSequence(firstImageResized);
		for (int i = 1; i <= images.size() - 1; i++) {
			BufferedImage nextImage = ImageIO.read(images.get(i));
			BufferedImage nextResized = resizeImage(nextImage, firstImage.getWidth(), firstImage.getWidth());
			writer.writeToSequence(nextResized);
		}
		writer.close();
		output.close();
		FileInputStream inputStreamGif = new FileInputStream(path.resolve("IHTM.gif").toFile());
		nativeImage = NativeImage.read(inputStreamGif);
	}


	@Override
	public void onInitialize() {
		LOGGER.info("It's Morbin' time!");
		ModConfigs.registerConfigs();
		ModItems.registerModItems();
		ModBlocks.registerModBlocks();
		ModBlockEntities.registerAllBlockEntities();
		readConfig();
		try {
			getImagesFromImgur();
		} catch (Exception e) {
			LOGGER.info(e.toString());
		}
	}
}
