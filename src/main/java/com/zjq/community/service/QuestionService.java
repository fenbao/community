package com.zjq.community.service;

import com.zjq.community.dto.PaginationDTO;
import com.zjq.community.dto.QuestionDTO;
import com.zjq.community.exception.CustomizeErrorCode;
import com.zjq.community.exception.CustomizeException;
import com.zjq.community.mapper.QuestionMapper;
import com.zjq.community.mapper.UserMapper;
import com.zjq.community.model.Question;
import com.zjq.community.model.QuestionExample;
import com.zjq.community.model.User;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @date 2022-01-20 22:38
 */
@Service
public class QuestionService {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private QuestionMapper questionMapper;

    public PaginationDTO list(Integer page, Integer size) {

        PaginationDTO paginationDTO = new PaginationDTO();
        Integer totalCount = (int)questionMapper.countByExample(new QuestionExample()); //分页总数
        paginationDTO.setPagination(totalCount, page, size);
        // 需要先settotalCount才能算出TotalPage 不然后面一句为null
        /*分页范围的控制 主要是为了应对因为前端页面传递的page出错而造成下面offset计算错然后sql语句运行出错*/
        if(page<1){
            page = 1;
        }
        if(page>paginationDTO.getTotalPage()){
            page = paginationDTO.getTotalPage();
        }

        //size * (page-1)
        Integer offset = size * (page-1);
        List<Question> questions = questionMapper.selectByExampleWithBLOBsWithRowbounds(new QuestionExample(),new RowBounds(offset,size)); //从question表中查出范围中的所有信息
        List<QuestionDTO> questionDTOList = new ArrayList<>();

        for (Question question : questions) {
            User user = userMapper.selectByPrimaryKey(question.getCreator());  // question表的creator和user表的id字段是对应的
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question, questionDTO); //直接使用spring提供的方法，将属性拷贝过去
            questionDTO.setUser(user);
            questionDTOList.add(questionDTO);
        }
        paginationDTO.setQuestions(questionDTOList);

        //return questionDTOList;  //这个时候返回的既有question信息又有用户信息
        return paginationDTO;
    }

    public PaginationDTO listById(Integer userId, Integer page, Integer size) {
        PaginationDTO paginationDTO = new PaginationDTO();
        QuestionExample questionExample = new QuestionExample();
        questionExample.createCriteria().andCreatorEqualTo(userId);
        Integer totalCount = (int)questionMapper.countByExample(questionExample); //根据Id查询分页总数
        paginationDTO.setPagination(totalCount, page, size);
        // 需要先settotalCount才能算出TotalPage 不然后面一句为null
        /*分页范围的控制 主要是为了应对因为前端页面传递的page出错而造成下面offset计算错然后sql语句运行出错*/
        if(page<1){
            page = 1;
        }
        if(page>paginationDTO.getTotalPage()){
            page = paginationDTO.getTotalPage();
        }

        //size * (page-1)
        Integer offset = size * (page-1);
        QuestionExample example = new QuestionExample();
        example.createCriteria().andCreatorEqualTo(userId);
        List<Question> questions = questionMapper.selectByExampleWithBLOBsWithRowbounds(example,new RowBounds(offset,size)); //从question表中查出范围中的所有信息
        //从question表中查出该用户发出提问的所有信息
        List<QuestionDTO> questionDTOList = new ArrayList<>();

        for (Question question : questions) {
            User user = userMapper.selectByPrimaryKey(question.getCreator());  // question表的creator和user表的id字段是对应的
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question, questionDTO); //直接使用spring提供的方法，将属性拷贝过去
            questionDTO.setUser(user);
            questionDTOList.add(questionDTO);
        }
        paginationDTO.setQuestions(questionDTOList);

        //return questionDTOList;  //这个时候返回的既有question信息又有用户信息
        return paginationDTO;
    }

    public QuestionDTO getById(Integer id) {
        Question question = questionMapper.selectByPrimaryKey(id);
        if(question == null){
            throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
        }
        QuestionDTO questionDTO = new QuestionDTO();
        BeanUtils.copyProperties(question, questionDTO);
        User user = userMapper.selectByPrimaryKey(question.getCreator());
        questionDTO.setUser(user);
        return questionDTO;
    }


    public void createOrUpdate(Question question) {
        if(question.getId() == null){
            question.setGmtCreate(System.currentTimeMillis());
            question.setGmtModified(question.getGmtModified());
            questionMapper.insert(question);
        }else{
            // 更新操作，这个时候也需要更新 ‘更新时间’
            question.setGmtModified(System.currentTimeMillis());
            Question updateQuestion = new Question();
            updateQuestion.setTitle(question.getTitle());
            updateQuestion.setTag(question.getTag());
            updateQuestion.setDescription(question.getDescription());
            QuestionExample example = new QuestionExample();
            example.createCriteria().andIdEqualTo(question.getId());
            questionMapper.updateByExampleSelective(updateQuestion,example);
        }
    }
}
