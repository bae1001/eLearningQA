<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<html>
<body>
<h2>Manual de usuario:</h2>
<h3>Pantallas:</h3>
<p><b>Página de login:</b> la página de login contiene un formulario de inicio de sesión, un link &quot;Acerca de&quot; que resume los objetivos y el funcionamiento de la aplicación, y un link &quot;Manual de usuario&quot; que muestra la versión web de este manual.</p>
<p><b>Página principal:&nbsp;</b>la página principal contiene una cabecera que indica el nombre de usuario con el que se ha registrado, un botón para desconectarse, un enlace para generar un informe global de todos los cursos, y una tabla con una lista de enlaces para generar los informes específicos de cada curso.</p>
<p><b>Página de informe:</b> la página de informe genera el informe dependiendo de cómo se haya accedido a esta, ya sea mediante el enlace &quot;Generar informe global&quot; o por medio de los enlaces a informes de cursos específicos.&nbsp;</p>
<p>Los informes contienen los siguientes elementos:</p>
<ul>
    <li><b>Puntuaciones por rol y perspectiva:</b> el informe contiene una matriz de roles y perspectivas que da distintas puntuaciones a cada uno de los roles (Diseñador, Facilitador, y Proveedor) en cada una de las tres perspectivas definidas (Pedagógica, Tecnológica, y Estratégica) según los resultados del análisis.</li>
    <li><b>Informe de fases:</b> el informe contiene también una tabla que muestra los resultados en cada una delas comprobaciones del análisis y las agrupa en las distintas fases del diseño instruccional (Diseño, Implementación, Realización, y Evaluación) además de dar una puntuación general.</li>
    <li><b>Lista de mejoras:</b> por último, el informe muestra una lista de motivos concretos que producen resultados negativos en el análisis para subsanarlos o tenerlos en cuenta de cara al futuro.</li>
</ul>
<p>El informe especifico muestra los resultados de las comprobaciones en el informe de fases de forma absoluta, es decir, que solo indica si el criterio se cumple o no, sin embargo, en el informe global, se muestra un resultado u otro &nbsp;dependiendo de la cantidad de cursos que satisfacen dicho criterio.</p>

<h3>Acciones del usuario:</h3>
<ul>
<li><b>Login:</b> para acceder a la aplicación son necesarias las credenciales de acceso a una cuenta de la plataforma Moodle a la que accede la aplicación (en el caso del prototipo es Mount &nbsp;Orange School). Debe introducir su usuario y contraseña en los campos &quot;Username&quot; y &quot;Password&quot;. Si quiere vaciar los campos pulse el botón &quot;Borrar&quot;. Para acceder a la página principal, pulse el botón &quot;Entrar&quot; tras haber introducido sus credenciales.</li>
<li><b>Desconectar:</b> desde la página principal, si desea finalizar su sesión, pulse el botón &quot;Desconectar&quot;. Esto invalidará sus credenciales y le impedirá acceder a la aplicación hasta que se registre de nuevo con unas credenciales válidas.</li>
<li><b>Generar informe específico:</b> la página principal muestra una tabla con todos los cursos en los que se encuentra matriculado el usuario registrado en formato de enlace. Al clicar un enlace, se generará un informe en una pestaña aparte del navegador que mostrará los resultados del análisis que ha realizado la aplicación sobre el curso correspondiente.</li><li><b>Generar informe global:</b> en la página principal hay un enlace llamado &quot;Generar informe global&quot;. Al hacer clic sobre este, se generará un informe en una pestaña aparte del navegador que mostrará un resumen de los análisis de todos los cursos en los que se encuentra matriculado el profesor.</li>
</ul>
<h3>Explicación de las comprobaciones de los informes:</h3>
<p>Las siguientes comprobaciones están relacionadas con los roles, fases, y perspectivas mencionados anteriormente. Los distintos procesos del diseño instruccional se encuentran divididos en fases, &nbsp;con ciertas perspectivas en mente, y son responsabilidad directa o indirecta de ciertos roles. Al estar las comprobaciones ligadas a esos procesos se muestran agrupadas por fases, y después de la explicación se indican los roles responsables e involucrados, además de las perspectivas correspondientes.</p>
<p><b>Diseño:</b></p>
<ul>
<li><b>Las opciones de progreso del estudiante están activadas</b>: se comprueba que estén habilitadas las opciones de progreso de los estudiantes en el curso.&nbsp;<u>Responsable:</u> Diseñador <u>Involucrados:</u> Facilitador <u>Perspectivas:</u> Pedagógica</li>
<li><b>Se proporcionan contenidos en diferentes formatos:</b> se comprueba que haya variedad de formatos en los recursos del curso. <u>Responsable:</u> Diseñador <u>Involucrados:</u> Facilitador y Proveedor <u>Perspectivas:</u> Pedagógica y Tecnológica</li>
<li><b>El curso tiene grupos:</b> se comprueba que existan grupos definidos en el curso. <u>Responsable:</u> Diseñador <u>Involucrados:</u> Facilitador y Proveedor <u>Perspectivas:</u> Pedagógica</li>
<li><b>El curso tiene actividades grupales:</b> se comprueba que existan actividades con entrega grupal habilitada en el curso. <u>Responsable:</u> Diseñador <u>Involucrados:</u> Facilitador y Proveedor <u>Perspectivas:</u> Pedagógica</li>
<li><b>Los estudiantes pueden ver las condiciones necesarias para completar una actividad:</b> se comprueba que esté habilitada la opción de mostrar las condiciones para completar una actividad en el curso. <u>Responsable:</u> Diseñador <u>Involucrados:</u> Facilitador y Proveedor <u>Perspectivas:</u> Pedagógica</li>
<li><b>Todas las actividades tienen la misma nota máxima en el calificador:</b> se comprueba que exista una consistencia en las notas máximas de los items de calificación (tareas, entregas, cuestionarios) del curso. <u>Responsable:</u> Diseñador <u>Involucrados:</u> Facilitador y Proveedor <u>Perspectivas:</u> Pedagógica</li>
</ul>
<p><b>Implementación:</b></p>
<ul>
<li><b>Los recursos están actualizados:</b> se comprueba que los recursos del curso tengan una fecha de creación reciente. <u>Responsable:</u> Diseñador <u>Involucrados:</u> Facilitador y Proveedor <u>Perspectivas:</u> Pedagógica y Tecnológica</li>
<li><b>Fechas de apertura y cierre de tareas son correctas:</b> se comprueba que las fechas de apertura y cierre de tareas y cuestionarios no se solapen de forma erronea con las fechas de inicio y fin del curso. <u>Responsable:</u> Facilitador <u>Involucrados:</u> Diseñador y Proveedor <u>Perspectivas:</u> Pedagógica y Tecnológica</li>
<li><b>Se detallan los criterios de evaluación:</b> se comprueba que exista en al menos una actividad una rúbrica o una guía de calificación en el curso. <u>Responsable:</u> Diseñador <u>Involucrados:</u> Facilitador y Proveedor <u>Perspectivas:</u> Pedagógica y Tecnológica</li>
<li><b>El calificador no tiene demasiado anidamiento:</b> se comprueba que la estructura de las categorías del calificador no sea demasiado enrevesada. <u>Responsable:</u> Diseñador <u>Involucrados:</u> Facilitador y Proveedor <u>Perspectivas:</u> Pedagógica y Estratégica</li>
<li><b>Los alumnos están divididos en grupos:</b> se comprueba que cada alumno pertenezca a un grupo. <u>Responsable:</u> Proveedor <u>Involucrados:</u> Diseñador <u>Perspectivas:</u> Tecnológica y Estratégica</li>
</ul>
<p><b>Realización:</b></p>
<ul>
<li><b>El profesor responde en los foros dentro del límite de 48 horas lectivas desde que se plantea la duda:</b> se comprueba que no hayan preguntas por parte de alumnos que estén sin responder en un tiempo razonable. <u>Responsable:</u> Facilitador <u>Involucrados:</u> Diseñador y Proveedor <u>Perspectivas:</u> Pedagógica y Tecnológica</li>
<li><b>Se ofrece retroalimentación de las tareas:</b> se comprueba que el profesor deje comentarios en la mayoría de calificaciones que haga. <u>Responsable:</u> Facilitador <u>Involucrados:</u> Diseñador y Proveedor <u>Perspectivas:</u> Pedagógica y Tecnológica</li>
<li><b>Las tareas están calificadas:</b> se comprueba que no hayan entregas de alumnos que hayan pasado una semana sin calificación. <u>Responsable:</u> Facilitador <u>Involucrados:</u> Diseñador y Proveedor <u>Perspectivas:</u> Pedagógica y Tecnológica</li>
<li><b>El calificador muestra cómo ponderan las diferentes tareas:</b> se comprueba que el calificador muestre los pesos de los items de calificación. <u>Responsable:</u> Facilitador <u>Involucrados:</u> Diseñador y Proveedor <u>Perspectivas:</u> Pedagógica y Tecnológica</li>
</ul>
<p><b>Evaluación:</b></p>
<ul>
<li><b>La mayoría de alumnos responden a los feedbacks:</b> se comprueba que no hayan muchos alumnos que no respondan a los feedbacks. <u>Responsable:</u> Proveedor <u>Involucrados:</u> Diseñador y Facilitador <u>Perspectivas:</u> Pedagógica, Tecnológica, y Estratégica</li>
<li><b>Se utilizan encuestas de opinión:</b> se comprueba que el curso contenga encuestas de opinión. <u>Responsable:</u> Proveedor <u>Involucrados:</u> Diseñador y Facilitador <u>Perspectivas:</u> Pedagógica, Tecnológica, y Estratégica</li></ul></body></html>