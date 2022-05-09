API service code by using Java Spring Boot that created to Blog Application.There are two parts, first is service about user such as login register and second part
is about article to add edit or search article. Moreover this API use access token and role to verify that user can access. 
#
UserController : register, login, edit profile, subscribe or unsubscribe user, get user detail
#
ArticleController : add article, edit article, delete article, get article detail, get list article, search article
#
you must implement this script below to database  in order to set set up role with access service.
#

USE [Blog]
GO

INSERT INTO [dbo].[role]
           ([access_service]
           ,[role_name])
     VALUES
           ('editProfile,getProfile,refreshToken,setPassword,getListFollow,addArticle,editArticle,deleteArticle,getUserDetail,searchArticle,getListArticleAuthor,getArticleDetail',
           'Author')

INSERT INTO [dbo].[role]
           ([access_service]
           ,[role_name])
     VALUES
           ('editProfile,getProfile,refreshToken,setPassword,getListFollow,addFollow,unFollow,subscribeArticle,unSubscribeArticle,getUserDetail,searchArticle,getListArticleReader,getArticleDetail',
           'Reader')
GO



