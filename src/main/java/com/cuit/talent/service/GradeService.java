package com.cuit.talent.service;

import com.cuit.talent.model.Grade;
import com.cuit.talent.model.QGrade;
import com.cuit.talent.model.UserGpa;
import com.cuit.talent.model.UserGrade;
import com.cuit.talent.repository.GradeRepository;
import com.cuit.talent.utils.valueobj.GradeAllSemesterGpa;
import com.cuit.talent.utils.valueobj.Message;
import com.querydsl.core.BooleanBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class GradeService {
    @Autowired
    private GradeRepository gradeRepository;
    @Autowired
    private UserGradeService userGradeService;
    @Autowired
    private UserGpaService userGpaService;

    public List<String> semesterList = Arrays.asList("第一学期","第二学期","第三学期","第四学期","第五学期","第六学期","第七学期","第八学期");
    public List<Grade> findAll(){
        List<Grade> grades = gradeRepository.findAll();
        return grades;
    }

    public Grade findByGrade(String grade){
        QGrade qGrade = QGrade.grade1;
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        booleanBuilder.and(qGrade.grade.eq(grade));
        Optional<Grade> existGrade = gradeRepository.findOne(booleanBuilder);
        if (existGrade.equals(Optional.empty())){
            return null;
        }
        return existGrade.get();
    }

    public Optional<Grade> findByGradeId(Integer id){
        QGrade qGrade = QGrade.grade1;
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        booleanBuilder.and(qGrade.id.eq(id));
        return gradeRepository.findOne(booleanBuilder);
    }

    public Message findByGradeIds(List<UserGrade> userGrades){
        List<Grade> grades = new ArrayList<>();
        Message message = new Message();
        try{
            userGrades.forEach(i-> grades.add(findByGradeId(i.getGradeByGradeId().getId()).get()));
        }catch (Exception e){
            message.setCode(0);
            message.setMsg("查找失败，不存在对应班级");
        }
        message.setCode(1);
        message.setData(grades);
        message.setMsg("查找成功");
        return message;
    }
    public Message findGradeGpa(Integer gradeId){
        int studentNum;
        Message message = new Message();
        GradeAllSemesterGpa gradeAllSemesterGpa = new GradeAllSemesterGpa();
        List<UserGrade> userGradeList = userGradeService.findByGradeId(gradeId);
        //List<UserGrade> list = userGradeList.stream().filter(i->i.getUserByUserId().getRoleByRoleId().getId() != 3).collect(Collectors.toList());
        studentNum = userGradeList.size();
        for(UserGrade userGrade:userGradeList){
            if(userGrade.getUserByUserId().getRoleByRoleId().getId()!=3){
                studentNum--;
                continue;
            }
            int i = 1;
            for(String semester:semesterList){
                UserGpa userGpa = userGpaService.findUserGpaByUserIdAndSemester(userGrade.getUserByUserId().getId(),semester);
                double gpa = (userGpa!=null)?userGpa.getGpa():0.0;
                switch (i){
                    case 1:
                        gradeAllSemesterGpa.setFirstSemesterGpa(gradeAllSemesterGpa.getFirstSemesterGpa()+gpa);
                        break;
                    case 2:
                        gradeAllSemesterGpa.setSecondSemesterGpa(gradeAllSemesterGpa.getSecondSemesterGpa()+gpa);
                        break;
                    case 3:
                        gradeAllSemesterGpa.setThirdlySemesterGpa(gradeAllSemesterGpa.getThirdlySemesterGpa()+gpa);
                        break;
                    case 4:
                        gradeAllSemesterGpa.setFourthlySemesterGpa(gradeAllSemesterGpa.getFourthlySemesterGpa()+gpa);
                        break;
                    case 5:
                        gradeAllSemesterGpa.setFifthSemesterGpa(gradeAllSemesterGpa.getFifthSemesterGpa()+gpa);
                        break;
                    case 6:
                        gradeAllSemesterGpa.setSixthSemesterGpa(gradeAllSemesterGpa.getSixthSemesterGpa()+gpa);
                        break;
                    case 7:
                        gradeAllSemesterGpa.setSeventhSemesterGpa(gradeAllSemesterGpa.getSeventhSemesterGpa()+gpa);
                        break;
                    case 8:
                        gradeAllSemesterGpa.setEighthSemesterGpa(gradeAllSemesterGpa.getEighthSemesterGpa()+gpa);
                        break;
                    default:
                        break;

                }
                i++;
            }
        }

        gradeAllSemesterGpa.setFirstSemesterGpa(gradeAllSemesterGpa.getFirstSemesterGpa()/studentNum);

        gradeAllSemesterGpa.setSecondSemesterGpa(gradeAllSemesterGpa.getSecondSemesterGpa()/studentNum);

        gradeAllSemesterGpa.setThirdlySemesterGpa(gradeAllSemesterGpa.getThirdlySemesterGpa()/studentNum);

        gradeAllSemesterGpa.setFourthlySemesterGpa(gradeAllSemesterGpa.getFourthlySemesterGpa()/studentNum);

        gradeAllSemesterGpa.setFifthSemesterGpa(gradeAllSemesterGpa.getFifthSemesterGpa()/studentNum);

        gradeAllSemesterGpa.setSixthSemesterGpa(gradeAllSemesterGpa.getSixthSemesterGpa()/studentNum);

        gradeAllSemesterGpa.setSeventhSemesterGpa(gradeAllSemesterGpa.getSeventhSemesterGpa()/studentNum);

        gradeAllSemesterGpa.setEighthSemesterGpa(gradeAllSemesterGpa.getEighthSemesterGpa()/studentNum);

        message.setData(gradeAllSemesterGpa);
        return message;

    }

}
