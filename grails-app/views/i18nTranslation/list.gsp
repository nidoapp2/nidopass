
<%@ page import="com.nidoapp.nidopub.domain.i18n.I18nTranslation" %>
<!DOCTYPE html>
<html>
	<head>
		<div>
			<ul>
				<g:each in="${i18nTranslationInstanceList}" status="i" var="i18nTranslationInstance">
					
						<li><g:link action="edit" id="${i18nTranslationInstance.id}">${fieldValue(bean: i18nTranslationInstance, field: "lang")}</g:link></li>
					
				</g:each>
			</ul>
		</div>
	</body>
</html>
