package spring.model.memo;
 
import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import spring.model2.IReplyDAO;
 
@Repository
public class ReplyDAO_memo implements IReplyDAO {
 
@Autowired
private SqlSessionTemplate sqlSessionTemplate ; 
 
public int rcount(int memono){
    return sqlSessionTemplate.selectOne("reply_memo.rcount", memono);
}
 
              
public void setSqlSessionTemplate(SqlSessionTemplate sqlSessionTemplate){
this.sqlSessionTemplate = sqlSessionTemplate;
}
 
public boolean create(ReplyDTO_memo dto){
boolean flag = false;
 
int cnt = (Integer)sqlSessionTemplate.insert("reply_memo.create", dto);
if(cnt>0) flag = true;
 
return flag;
}
 
public ReplyDTO_memo read(int rnum){
 
return(ReplyDTO_memo)sqlSessionTemplate.selectOne("reply_memo.read", rnum);
 
}
 
public boolean update(ReplyDTO_memo dto){
boolean flag = false;
 
int cnt = sqlSessionTemplate.update("reply_memo.update", dto);
if(cnt>0)flag = true;
 
return flag;
}
 
public List<ReplyDTO_memo> list(Map map){
 
return sqlSessionTemplate.selectList("reply_memo.list", map);
}
 
 
public int total(int memono){
return (Integer)sqlSessionTemplate.selectOne("reply_memo.total",memono);
}
 
public boolean delete(int rnum){
boolean flag = false;
int cnt = sqlSessionTemplate.delete("reply_memo.delete", rnum);
if(cnt>0) flag = true;
 
return flag;
}

/** 하나의 글의  여러댓글들 삭제 */
public int bdelete(int memono) throws Exception{
   return sqlSessionTemplate.delete("reply_memo.bdelete", memono);
   
}
}
 