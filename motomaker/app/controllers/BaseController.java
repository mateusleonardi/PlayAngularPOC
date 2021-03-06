package controllers;

import java.io.IOException;
import java.util.concurrent.Callable;

import com.fasterxml.jackson.databind.JsonNode;

import exception.BusinessException;
import play.db.ebean.Model;
import play.mvc.Controller;
import play.mvc.Result;
import util.webservice.JsonObjectParser;

public abstract class BaseController extends Controller {
	
	protected static <T extends Model> T getModelFromRequest(Class<T> type) throws BusinessException {
		try {
			JsonNode json = request().body().asJson();
			return JsonObjectParser.Deserialize(json, type);
		} catch (Exception e) {
			throw new BusinessException("Parameters invalid.");
		}
	}
	
	protected static Result executionHandler(Callable<Result> callable) {
		try {
			return callable.call();
		} catch (Exception e) {
			return internalServerError(e.getMessage());
		}
	}
}