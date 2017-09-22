package controller;

import java.io.File;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import model.Game;
import model.pieces.Piece;

public class ChessService {
	
	public boolean saveToXML(Game game) {
		List<Piece> piecesP1 = game.getP1().getPiecesOnBoard();
		List<Piece> piecesP2 = game.getP2().getPiecesOnBoard();
		String nameP1 = game.getP1().getName();
		String nameP2 = game.getP2().getName();
		boolean isActualP1 = game.getP1().isActual();
		boolean isActualP2 = game.getP2().isActual();

		DocumentBuilderFactory dBF = DocumentBuilderFactory.newInstance();
		try {
			DocumentBuilder builder = dBF.newDocumentBuilder();
			Document doc = builder.newDocument();

			Element properties = doc.createElement("gamedata");
			doc.appendChild(properties);
			Element player1 = doc.createElement("player");
			Element player2 = doc.createElement("player");
			Element isTurn1 = doc.createElement("isActual");
			Element isTurn2 = doc.createElement("isActual");
			Element name1 = doc.createElement("name");
			Element name2 = doc.createElement("name");
			Element pieces1 = doc.createElement("pieces");
			Element pieces2 = doc.createElement("pieces");

			// create the hierarchy
			// doc.appendChild(properties);
			properties.appendChild(player1);
			player1.appendChild(name1);
			name1.appendChild(doc.createTextNode(nameP1));
			player1.appendChild(isTurn1);
			isTurn1.appendChild(doc.createTextNode(String.valueOf(isActualP1)));
			player1.appendChild(pieces1);
			for (Piece p : piecesP1) {
				Element piece = doc.createElement("piece");
				Element x = doc.createElement("x");
				// xT = sb.toString();
				x.appendChild(doc.createTextNode("" + p.getPosition().getX()));
				piece.appendChild(x);

				Element y = doc.createElement("y");
				// yT = sb.toString();
				y.appendChild(doc.createTextNode("" + p.getPosition().getY()));
				piece.appendChild(y);

				Element value = doc.createElement("value");
				value.appendChild(doc.createTextNode("" + p.getValue()));
				piece.appendChild(value);

				Element isFirstMove = doc.createElement("isFirstMove");
				isFirstMove.appendChild(doc.createTextNode("" + p.isFirstMove()));
				piece.appendChild(isFirstMove);

				pieces1.appendChild(piece);
			}

			/**/

			properties.appendChild(player2);
			player2.appendChild(name2);
			name2.appendChild(doc.createTextNode(nameP2));
			player2.appendChild(isTurn2);
			isTurn2.appendChild(doc.createTextNode(String.valueOf(isActualP2)));
			player2.appendChild(pieces2);
			for (Piece p : piecesP2) {
				Element piece = doc.createElement("piece");
				Element x = doc.createElement("x");
				// xT = sb.toString();
				x.appendChild(doc.createTextNode("" + p.getPosition().getX()));
				piece.appendChild(x);

				Element y = doc.createElement("y");
				// yT = sb.toString();
				y.appendChild(doc.createTextNode("" + p.getPosition().getY()));
				piece.appendChild(y);

				Element value = doc.createElement("value");
				value.appendChild(doc.createTextNode("" + p.getValue()));
				piece.appendChild(value);

				Element isFirstMove = doc.createElement("isFirstMove");
				isFirstMove.appendChild(doc.createTextNode("" + p.isFirstMove()));
				piece.appendChild(isFirstMove);
				pieces2.appendChild(piece);
			}

			TransformerFactory tf = TransformerFactory.newInstance();
			Transformer t = tf.newTransformer();

			Source src = new DOMSource(doc);
			// String path_fixed = path.substring(0, 2) + path.substring(2);
			Result dest = new StreamResult(new File("save.xml"));
			t.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
			t.setOutputProperty(OutputKeys.INDENT, "yes");
			t.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
			t.transform(src, dest);
			return true;
		} catch (ParserConfigurationException e) {
			return false;
		} catch (TransformerConfigurationException e) {
			return false;
		} catch (TransformerException e) {
			return false;
		}
	}

	public Game loadFromXML(Game game) {
		game.clearGame();
		String nameP1 = game.getP1().getName();
		String nameP2 = game.getP1().getName();
		boolean isActualP1 = game.getP1().isActual();
		boolean isActualP2 = game.getP2().isActual();
		try {
			File xmlFile = new File("save.xml");

			DocumentBuilderFactory dBFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dBFactory.newDocumentBuilder();

			Document doc = dBuilder.parse(xmlFile);
			Element root = doc.getDocumentElement();
//			System.out.println("Root: " + doc.getDocumentElement().getNodeName() + "\n");

			NodeList playerList = root.getElementsByTagName("player");
			for (int i = 0; i < playerList.getLength(); i++) {
				if (i == 0) {
					Node playerNode = playerList.item(i);
					if (playerNode.getNodeType() == Node.ELEMENT_NODE) {
						Element playerElement = (Element) playerNode;
						isActualP1 = Boolean
								.valueOf(playerElement.getElementsByTagName("isActual").item(0).getTextContent());
						nameP1 = playerElement.getElementsByTagName("name").item(0).getTextContent();
						game.getP1().setName(nameP1);
						game.getP1().setActual(isActualP1);

						NodeList pieces = playerElement.getElementsByTagName("pieces").item(0).getChildNodes();
						for (int j = 0; j < pieces.getLength(); j++) {
							Node piece = pieces.item(j);
							if (piece.getNodeType() == Node.ELEMENT_NODE) {
								Element pElement = (Element) piece;
								int x = Integer.valueOf(pElement.getElementsByTagName("x").item(0).getTextContent());
								int y = Integer.valueOf(pElement.getElementsByTagName("y").item(0).getTextContent());
								int value = Integer
										.valueOf(pElement.getElementsByTagName("value").item(0).getTextContent());
								boolean isFirstMove = Boolean
										.valueOf(pElement.getElementsByTagName("isFirstMove").item(0).getTextContent());
								game.getP1().addPieceOnBoard(game.createPiece(value, x, y, isFirstMove));
								game.getBoard().setValue(value, x, y);
							}
						}
//						System.out.println();
					}
				} else {
					Node playerNode = playerList.item(i);
					if (playerNode.getNodeType() == Node.ELEMENT_NODE) {
						Element playerElement = (Element) playerNode;
						isActualP2 = Boolean
								.valueOf(playerElement.getElementsByTagName("isActual").item(0).getTextContent());
						nameP2 = playerElement.getElementsByTagName("name").item(0).getTextContent();
						game.getP2().setName(nameP2);
						game.getP2().setActual(isActualP2);
//						System.out.println("isActual: " + String
//								.valueOf(playerElement.getElementsByTagName("isActual").item(0).getTextContent()));
//						System.out.println(
//								"name: " + playerElement.getElementsByTagName("name").item(0).getTextContent());

						NodeList pieces = playerElement.getElementsByTagName("pieces").item(0).getChildNodes();
						for (int j = 0; j < pieces.getLength(); j++) {
							Node piece = pieces.item(j);
							if (piece.getNodeType() == Node.ELEMENT_NODE) {
								Element pElement = (Element) piece;
								int x = Integer.valueOf(pElement.getElementsByTagName("x").item(0).getTextContent());
//								System.out.println("x: " + x);
								int y = Integer.valueOf(pElement.getElementsByTagName("y").item(0).getTextContent());
//								System.out.println("y: " + y);
								int value = Integer
										.valueOf(pElement.getElementsByTagName("value").item(0).getTextContent());
//								System.out.println("value: " + value);
								boolean isFirstMove = Boolean
										.valueOf(pElement.getElementsByTagName("isFirstMove").item(0).getTextContent());
								game.getP2().addPieceOnBoard(game.createPiece(value, x, y, isFirstMove));
								game.getBoard().setValue(value, x, y);
							}
						}
//						System.out.println();
					}
				}

			}

			game.refreshPiecesOnBoard();
			return game;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

}
