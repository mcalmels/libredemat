
package org.libredemat.business.request.leisure.music;

import java.io.Serializable;
import java.math.BigInteger;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.joda.time.LocalTime;

import net.sf.oval.constraint.AssertValid;
import org.apache.xmlbeans.XmlOptions;
import org.libredemat.business.authority.*;
import org.libredemat.business.payment.*;
import org.libredemat.business.request.*;
import org.libredemat.business.request.annotation.*;
import org.libredemat.business.users.*;
import org.libredemat.xml.common.*;
import org.libredemat.xml.request.leisure.music.*;
import org.libredemat.service.request.condition.IConditionChecker;

/**
 * Generated class file, do not edit !
 */
public class MusicSchoolRegistrationRequest extends Request implements Serializable {

    private static final long serialVersionUID = 1L;

    public static final Map<String, IConditionChecker> conditions = MusicSchoolRegistrationRequestData.conditions;

    @AssertValid(message = "")
    private MusicSchoolRegistrationRequestData musicSchoolRegistrationRequestData;

    public MusicSchoolRegistrationRequest(RequestData requestData, MusicSchoolRegistrationRequestData musicSchoolRegistrationRequestData) {
        super(requestData);
        this.musicSchoolRegistrationRequestData = musicSchoolRegistrationRequestData;
    }

    public MusicSchoolRegistrationRequest() {
        super();
        this.musicSchoolRegistrationRequestData = new MusicSchoolRegistrationRequestData();
        Map<String, Object> stepState;
        
          stepState = new HashMap<String, Object>(4);
          stepState.put("state", "uncomplete");
          stepState.put("required", false);
          stepState.put("errorMsg", null);
          stepState.put("invalidFields", new ArrayList<String>());
          getStepStates().put("homeFolder", stepState);
        
          stepState = new HashMap<String, Object>(4);
          stepState.put("state", "unavailable");
          stepState.put("required", true);
          stepState.put("errorMsg", null);
          stepState.put("invalidFields", new ArrayList<String>());
          getStepStates().put("registration", stepState);
        
          stepState = new HashMap<String, Object>(4);
          stepState.put("state", "unavailable");
          stepState.put("required", true);
          stepState.put("errorMsg", null);
          stepState.put("invalidFields", new ArrayList<String>());
          getStepStates().put("rules", stepState);
        
          stepState = new HashMap<String, Object>(4);
          stepState.put("state", "unavailable");
          stepState.put("required", false);
          stepState.put("errorMsg", null);
          stepState.put("invalidFields", new ArrayList<String>());
          getStepStates().put("document", stepState);
        
          stepState = new HashMap<String, Object>(4);
          stepState.put("state", "unavailable");
          stepState.put("required", true);
          stepState.put("errorMsg", null);
          stepState.put("invalidFields", new ArrayList<String>());
          getStepStates().put("validation", stepState);
        
    }

    /**
     * Reserved for RequestDAO !
     */
    @Override
    public MusicSchoolRegistrationRequestData getSpecificData() {
        return musicSchoolRegistrationRequestData;
    }

    /**
     * Reserved for RequestDAO !
     */
    public void setSpecificData(MusicSchoolRegistrationRequestData musicSchoolRegistrationRequestData) {
        this.musicSchoolRegistrationRequestData = musicSchoolRegistrationRequestData;
    }

    @Override
    public final String modelToXmlString() {
        MusicSchoolRegistrationRequestDocument object = this.modelToXml();
        XmlOptions opts = new XmlOptions();
        opts.setSavePrettyPrint();
        opts.setSavePrettyPrintIndent(4);
        opts.setUseDefaultNamespace();
        opts.setCharacterEncoding("UTF-8");
        return object.xmlText(opts);
    }

    @Override
    public final MusicSchoolRegistrationRequestDocument modelToXml() {
        
        Calendar calendar = Calendar.getInstance();
        LocalTime localTime = new LocalTime();
        Date date = null;
        MusicSchoolRegistrationRequestDocument musicSchoolRegistrationRequestDoc = MusicSchoolRegistrationRequestDocument.Factory.newInstance();
        MusicSchoolRegistrationRequestDocument.MusicSchoolRegistrationRequest musicSchoolRegistrationRequest = musicSchoolRegistrationRequestDoc.addNewMusicSchoolRegistrationRequest();
        super.fillCommonXmlInfo(musicSchoolRegistrationRequest);
        int i = 0;
        
        i = 0;
        if (getActivity() != null) {
            org.libredemat.xml.common.LocalReferentialDataType[] activityTypeTab = new org.libredemat.xml.common.LocalReferentialDataType[getActivity().size()];
            for (LocalReferentialData object : getActivity()) {
              activityTypeTab[i++] = LocalReferentialData.modelToXml(object);
            }
            musicSchoolRegistrationRequest.setActivityArray(activityTypeTab);
        }
      
        if (getRulesAndRegulationsAcceptance() != null)
            musicSchoolRegistrationRequest.setRulesAndRegulationsAcceptance(getRulesAndRegulationsAcceptance().booleanValue());
      
        return musicSchoolRegistrationRequestDoc;
    }

    @Override
    public final MusicSchoolRegistrationRequestDocument.MusicSchoolRegistrationRequest modelToXmlRequest() {
        return modelToXml().getMusicSchoolRegistrationRequest();
    }

    public static MusicSchoolRegistrationRequest xmlToModel(MusicSchoolRegistrationRequestDocument musicSchoolRegistrationRequestDoc) {
        MusicSchoolRegistrationRequestDocument.MusicSchoolRegistrationRequest musicSchoolRegistrationRequestXml = musicSchoolRegistrationRequestDoc.getMusicSchoolRegistrationRequest();
        Calendar calendar = Calendar.getInstance();
        LocalTime localTime = new LocalTime();
        List list = new ArrayList();
        MusicSchoolRegistrationRequest musicSchoolRegistrationRequest = new MusicSchoolRegistrationRequest();
        musicSchoolRegistrationRequest.fillCommonModelInfo(musicSchoolRegistrationRequest, musicSchoolRegistrationRequestXml);
        
        List<org.libredemat.business.request.LocalReferentialData> activityList = new ArrayList<org.libredemat.business.request.LocalReferentialData>(musicSchoolRegistrationRequestXml.sizeOfActivityArray());
        for (LocalReferentialDataType object : musicSchoolRegistrationRequestXml.getActivityArray()) {
            activityList.add(org.libredemat.business.request.LocalReferentialData.xmlToModel(object));
        }
        musicSchoolRegistrationRequest.setActivity(activityList);
      
        musicSchoolRegistrationRequest.setRulesAndRegulationsAcceptance(Boolean.valueOf(musicSchoolRegistrationRequestXml.getRulesAndRegulationsAcceptance()));
      
        return musicSchoolRegistrationRequest;
    }

    @Override
    public MusicSchoolRegistrationRequest clone() {
        MusicSchoolRegistrationRequest clone = new MusicSchoolRegistrationRequest(getRequestData().clone(), musicSchoolRegistrationRequestData.clone());
        Map<String, Object> stepState;
        
          stepState = new HashMap<String, Object>(4);
          stepState.put("state", "uncomplete");
          stepState.put("required", false);
          stepState.put("errorMsg", null);
          stepState.put("invalidFields", new ArrayList<String>());
          clone.getStepStates().put("homeFolder", stepState);
        
          stepState = new HashMap<String, Object>(4);
          stepState.put("state", "unavailable");
          stepState.put("required", true);
          stepState.put("errorMsg", null);
          stepState.put("invalidFields", new ArrayList<String>());
          clone.getStepStates().put("registration", stepState);
        
          stepState = new HashMap<String, Object>(4);
          stepState.put("state", "unavailable");
          stepState.put("required", true);
          stepState.put("errorMsg", null);
          stepState.put("invalidFields", new ArrayList<String>());
          clone.getStepStates().put("rules", stepState);
        
          stepState = new HashMap<String, Object>(4);
          stepState.put("state", "unavailable");
          stepState.put("required", false);
          stepState.put("errorMsg", null);
          stepState.put("invalidFields", new ArrayList<String>());
          clone.getStepStates().put("document", stepState);
        
          stepState = new HashMap<String, Object>(4);
          stepState.put("state", "unavailable");
          stepState.put("required", true);
          stepState.put("errorMsg", null);
          stepState.put("invalidFields", new ArrayList<String>());
          clone.getStepStates().put("validation", stepState);
        
        return clone;
    }

  
    public final void setActivity(final List<org.libredemat.business.request.LocalReferentialData> activity) {
        musicSchoolRegistrationRequestData.setActivity(activity);
    }

    
    public final List<org.libredemat.business.request.LocalReferentialData> getActivity() {
        return musicSchoolRegistrationRequestData.getActivity();
    }
  
    public final void setRulesAndRegulationsAcceptance(final Boolean rulesAndRegulationsAcceptance) {
        musicSchoolRegistrationRequestData.setRulesAndRegulationsAcceptance(rulesAndRegulationsAcceptance);
    }

    @IsRulesAcceptance
    public final Boolean getRulesAndRegulationsAcceptance() {
        return musicSchoolRegistrationRequestData.getRulesAndRegulationsAcceptance();
    }
  
}
